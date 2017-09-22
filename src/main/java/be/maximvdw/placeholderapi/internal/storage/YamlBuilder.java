package be.maximvdw.placeholderapi.internal.storage;

import be.maximvdw.placeholderapi.internal.utils.NumberUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * YamlBuilder
 * <p>
 * YAML Builder
 *
 * @author Maxim Van de Wynckel (Maximvdw)
 * @version 25-08-2014
 * @project MVdW Software Plugin Interface
 * @site http://www.mvdw-software.be/
 */
public class YamlBuilder extends YamlSection {
    private int idx = 0;

    public YamlBuilder() {

    }

    public YamlBuilder(String config, int indentSpaces) {
        try (BufferedReader br = new BufferedReader(new StringReader(config))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            for (int i = idx; i < lines.size(); i++) {
                YamlPart part = readPart(null, lines, 0, indentSpaces);
                if (part != null) {
                    addPart(part);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlPart readPart(YamlSection parent, List<String> lines, int level, int indentSpaces) throws IOException {
        if (idx >= lines.size()) {
            return parent;
        }
        String comment = null;
        String line = lines.get(idx);
        if (line.contains("#")) {
            comment = line.substring(line.indexOf("#", 0) + 1);
            line = line.substring(0, line.indexOf("#", 0));
        }

        String tabCheck = "";
        for (int i = 0; i < level; i++) {
            tabCheck += "  ";
        }

        if (line.trim().equals("") && comment != null) {
            if (!line.startsWith(tabCheck)) {
                return parent;
            }
            // Comment
            if (parent == null) {
                idx++;
                return new YamlCommentPart(comment);
            } else {
                parent.addPart(comment);
                idx++;
                return readPart(parent, lines, level, indentSpaces);
            }
        } else if (line.trim().equals("")) {
            // Empty line
            if (parent == null) {
                idx++;
                return new YamlEmptyPart();
            } else {
                parent.addEmptyPart();
                idx++;
                return readPart(parent, lines, level, indentSpaces);
            }
        } else {
            if (line.trim().startsWith("-")) {
                // List
                YamlStringListPart newParent = new YamlStringListPart(((YamlSectionPart) parent).getKey());
                return readList(newParent, lines);
            } else if (line.contains(":")) {
                if (!line.startsWith(tabCheck)) {
                    return parent;
                }
                // Key/value or section
                if (line.trim().endsWith(":")) {
                    // Section
                    YamlSection newParent = new YamlSectionPart(line.trim().substring(0, line.trim().length() - 1));
                    if (parent == null) {
                        idx++;
                        return readPart(newParent, lines, level + 1, indentSpaces);
                    } else {
                        idx++;
                        YamlPart part = readPart(newParent, lines, level + 1, indentSpaces);
                        if (part != null) {
                            parent.addPart(part);
                        }
                        return readPart(parent, lines, level, indentSpaces);
                    }
                } else {
                    String key = line.trim().substring(0, line.trim().indexOf(":", 0));
                    String value = line.trim().substring(key.length() + 1).trim();
                    if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
                        // String
                        value = value.substring(1, value.length() - 1);
                        if (parent == null) {
                            idx++;
                            return new YamlKeyValuePart(key, value);
                        } else {
                            parent.addPart(key, value);
                            idx++;
                            return readPart(parent, lines, level, indentSpaces);
                        }
                    } else if (value.equals("true") || value.equals("false")) {
                        boolean valueBool = Boolean.valueOf(value);
                        if (parent == null) {
                            idx++;
                            return new YamlKeyValuePart(key, valueBool);
                        } else {
                            parent.addPart(key, valueBool);
                            idx++;
                            return readPart(parent, lines, level, indentSpaces);
                        }
                    } else {
                        // Something else
                        if (NumberUtils.isInteger(value)) {
                            if (parent == null) {
                                idx++;
                                return new YamlKeyValuePart(key, Integer.parseInt(value));
                            } else {
                                parent.addPart(key, Integer.parseInt(value));
                                idx++;
                                return readPart(parent, lines, level, indentSpaces);
                            }
                        } else if (NumberUtils.isNumber(value)) {
                            if (parent == null) {
                                idx++;
                                return new YamlKeyValuePart(key, Double.parseDouble(value));
                            } else {
                                parent.addPart(key, Double.parseDouble(value));
                                idx++;
                                return readPart(parent, lines, level, indentSpaces);
                            }
                        }
                    }
                }
            }
        }
        return new YamlEmptyPart();
    }

    private YamlStringListPart readList(YamlStringListPart list, List<String> lines) throws IOException {
        if (idx >= lines.size()) {
            return list;
        }
        String line = lines.get(idx);
        if (line.trim().startsWith("-")) {
            String item = line.trim().substring(1).trim().substring(1);
            item = item.substring(0, item.length() - 1);
            list.addItem(item);
            idx++;
            return readList(list, lines);
        } else {
            return list;
        }
    }


    public boolean writeToFile(File file) {
        FileOutputStream fop = null;

        try {
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = toString().getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static class YamlCommentPart extends YamlPart {
        private String comment = "";

        public YamlCommentPart(String comment) {
            setComment(comment);
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Override
        public String toString() {
            return "#" + comment;
        }
    }

    public static class YamlKeyValuePart extends YamlPart {
        private String key = "";
        private Object value = "";

        public YamlKeyValuePart(String key, Object value) {
            setKey(key);
            setValue(value);
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (value instanceof String) {
                return new YamlStringPart(key, value.toString()).toString();
            }
            return getKey() + ": " + value;
        }

        public void loadConfig(String path, YamlConfiguration config) {
            setValue(config.get(path.equals("") ? getKey() : path + "." + getKey()));
        }

        public void loadNewConfig(String path, String key, YamlConfiguration config) {
            setValue(config.get(path.equals("") ? getKey() : path + "." + key));
            setKey(key);
        }
    }

    public static class YamlStringPart extends YamlKeyValuePart {

        public YamlStringPart(String key, String value) {
            super(key, value);
        }

        @Override
        public String toString() {
            return getKey() + ": \"" + sanitize(getValue().toString()) + "\"";
        }
    }

    public static class YamlIntegerPart extends YamlKeyValuePart {

        public YamlIntegerPart(String key, int value) {
            super(key, value);
        }

    }

    public static class YamlEmptyPart extends YamlPart {
        @Override
        public String toString() {
            return "";
        }
    }

    public static class YamlStringListPart extends YamlPart {
        private String key = "";
        private List<String> items = new ArrayList<String>();

        public YamlStringListPart(String key) {
            setKey(key);
        }

        public YamlStringListPart addItem(String item) {
            items.add(item);
            return this;
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void loadConfig(String path, YamlConfiguration config) {
            setItems(config.getStringList(path.equals("") ? getKey() : path + "." + getKey()));
        }

        public void loadNewConfig(String path, String key, YamlConfiguration config) {
            setKey(key);
            setItems(config.getStringList(path.equals("") ? getKey() : path + "." + getKey()));
        }

        public String toString() {
            String result = getKey() + ": ";
            if (getItems().size() == 0)
                result += "[]" + System.getProperty("line.separator");
            else {
                result += System.getProperty("line.separator");
                for (String item : getItems()) {
                    result += "- \"" + sanitize(item) + "\"" + System.getProperty("line.separator");

                }
            }
            return result;
        }
    }

    public static class YamlSectionPart extends YamlSection {
        private String key = "";

        public YamlSectionPart(String key) {
            setKey(key);
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String toString() {
            String result = getKey() + ":" + System.getProperty("line.separator");
            for (YamlPart part : getParts()) {
                String[] lines = part.toString().split(System.getProperty("line.separator"));
                for (String line : lines) {
                    result += "  " + line + System.getProperty("line.separator");
                }
            }

            return result;
        }
    }

    private static String sanitize(String input) {
        String output = input;
        output = output.replace("\"", "\\\"");
        return output;
    }
}
