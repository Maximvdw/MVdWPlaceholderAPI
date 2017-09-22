package be.maximvdw.placeholderapi.internal.storage;

import be.maximvdw.placeholderapi.internal.storage.YamlBuilder.*;

import java.util.ArrayList;
import java.util.List;

public class YamlSection extends YamlPart{
	// Yaml parts
	private List<YamlPart> parts = new ArrayList<YamlPart>();
	
	/**
	 * Add a YAML part
	 * 
	 * @param part
	 *            YAML part
	 * @return Yaml Builder
	 */
	public YamlSection addPart(YamlPart part) {
		part.setParent(this);
		parts.add(part);
		return this;
	}

	/**
	 * Add a YAML part
	 * 
	 * @param part
	 *            comment
	 * @return Yaml Builder
	 */
	public YamlSection addPart(String comment) {
		addPart(new YamlCommentPart(comment));
		return this;
	}

	/**
	 * Add an empty YAML part
	 * 
	 * @return Yaml builder
	 */
	public YamlSection addEmptyPart() {
		addPart(new YamlEmptyPart());
		return this;
	}

	/**
	 * Add a part
	 * 
	 * @param key
	 *            Key
	 * @param value
	 *            Value
	 * @return YamlBuilder
	 */
	public YamlSection addPart(String key, Object value) {
		addPart(new YamlKeyValuePart(key, value));
		return this;
	}
	
	/**
	 * Add a yaml section
	 * @param section section
	 * @return Yaml section part
	 */
	public YamlSection addSection(String section){
		YamlSectionPart sectionPart = new YamlSectionPart(section);
		sectionPart.setParent(this);
		addPart(sectionPart);
		return sectionPart;
	}
	
	/**
	 * Add a YAML part
	 * 
	 * @param part
	 *            comment
	 * @return Yaml Builder
	 */
	public YamlSection comment(String comment){
		addPart(comment);
		return this;
	}

	/**
	 * Create a list
	 * @param name list name
	 * @return list name
	 */
	public YamlStringListPart list(String name){
		YamlStringListPart list = new YamlStringListPart(name);
		addPart(list);
		return list;
	}

	/**
	 * Get Value
	 * 
	 * @param path
	 *            Path
	 * @return Object
	 */
	public Object getValue(String path) {
		String[] keys = path.split(".");
		if (keys.length == 0)
			return null;
		for (YamlPart part : getParts()) {
			if (part instanceof YamlSectionPart) {
				if (keys.length > 1) {
					String newPath = keys[1];
					for (int i = 2; i < keys.length; i++) {
						newPath += "." + keys[i];
					}
					return getValue(newPath);
				} else {
					return null;
				}
			} else if (part instanceof YamlKeyValuePart && keys.length == 1) {
				if (((YamlKeyValuePart) part).getKey().equalsIgnoreCase(keys[0])) {
					return ((YamlKeyValuePart) part).getValue();
				}
			}
		}

		return null;
	}

	
	/**
	 * Does the yaml part has parts
	 * 
	 * @return Parts
	 */
	public boolean hasParts() {
		return parts.size() > 0;
	}
	
	
	/**
	 * Get YAML Parts
	 * 
	 * @return yaml parts
	 */
	public List<YamlPart> getParts(){
		return parts;
	}

	/**
	 * Set YAML Parts
	 * 
	 * @param parts
	 *            yaml parts
	 */
	public void setParts(List<YamlPart> parts){
		this.parts = parts;
	}
	
	@Override
	public String toString(){
		String result = "";
		for (YamlPart part : getParts()) {
			result += part.toString() + System.getProperty("line.separator");
		}
		return result;
	}
	
	public String toCode(){
		String code = "";
		
		return code;
	}
}
