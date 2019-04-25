package be.maximvdw.placeholderapi.internal;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.internal.utils.ListUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Calendar;

public class PlaceholderConversion {
    public static void convertOnlineCalendarPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                         final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_week", description + " week in year",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.getWeekYear();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_timeinmillis", description + " time in millis",
                new OnlinePlaceholderReplacer<Long>(Long.class) {

                    @Override
                    public Long getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0L;
                        return cal.getTimeInMillis();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_firstdayofweek", description + " first day of the week",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.getFirstDayOfWeek();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_dayofmonth", description + " day of month",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_MONTH);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_dayofweek", description + " day of week",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_WEEK);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_dayofweekinmonth", description + " day of week in month",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_dayofyear", description + " day of year",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_YEAR);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_hour", description + " hour (am/pm)",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.HOUR);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_dayofmonth", description + " hour (24h)",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.HOUR_OF_DAY);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_era", description + " era",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.ERA);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_millisecond", description + " millisecond",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.MILLISECOND);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_minute", description + " minute",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.MINUTE);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_month", description + " month",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.MONTH);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_second", description + " second",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.SECOND);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_weekofmonth", description + " week of month",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.WEEK_OF_MONTH);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_weekofyear", description + " week of year",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.WEEK_OF_YEAR);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_year", description + " year",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.YEAR);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_dstoffset", description + " DST Offset",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DST_OFFSET);
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_zoneoffset", description + " zone offset",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.ZONE_OFFSET);
                    }
                });
    }

    public static void convertOfflineCalendarPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean requiresplayer,
                                                          final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_week", description + " week in year", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.getWeekYear();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_timeinmillis", description + " time in millis",
                requiresplayer, new PlaceholderReplacer<Long>(Long.class) {

                    @Override
                    public Long getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0L;
                        return cal.getTimeInMillis();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_firstdayofweek", description + " first day of the week",
                requiresplayer, new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.getFirstDayOfWeek();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_dayofmonth", description + " day of month", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_MONTH);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_dayofweek", description + " day of week", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_WEEK);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_dayofweekinmonth", description + " day of week in month",
                requiresplayer, new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_dayofyear", description + " day of year", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DAY_OF_YEAR);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_hour", description + " hour (am/pm)", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.HOUR);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_dayofmonth", description + " hour (24h)", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.HOUR_OF_DAY);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_era", description + " era", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.ERA);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_millisecond", description + " millisecond", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.MILLISECOND);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_minute", description + " minute", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.MINUTE);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_month", description + " month", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.MONTH);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_second", description + " second", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.SECOND);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_weekofmonth", description + " week of month",
                requiresplayer, new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.WEEK_OF_MONTH);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_weekofyear", description + " week of year", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.WEEK_OF_YEAR);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_year", description + " year", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.YEAR);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_dstoffset", description + " DST Offset", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.DST_OFFSET);
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_zoneoffset", description + " zone offset", requiresplayer,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Calendar cal = (Calendar) replacer.getResult(event);
                        if (cal == null)
                            return 0;
                        return cal.get(Calendar.ZONE_OFFSET);
                    }
                });
    }

    public static void convertOnlineLocationPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                         final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_x", description + " X",
                new OnlinePlaceholderReplacer<Double>(Double.class) {

                    @Override
                    public Double getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0.0;
                        return loc.getX();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_y", description + " X",
                new OnlinePlaceholderReplacer<Double>(Double.class) {

                    @Override
                    public Double getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0.0;
                        return loc.getY();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_z", description + " X",
                new OnlinePlaceholderReplacer<Double>(Double.class) {

                    @Override
                    public Double getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0.0;
                        return loc.getZ();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_blockx", description + " X rounded",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getBlockX();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_blocky", description + " Y rounded",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getBlockY();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_blockz", description + " Z rounded",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getBlockZ();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_world", description,
                new OnlinePlaceholderReplacer<World>(World.class) {

                    @Override
                    public World getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return null;
                        return loc.getWorld();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_yaw", description + " yaw",
                new OnlinePlaceholderReplacer<Float>(Float.class) {

                    @Override
                    public Float getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0F;
                        return loc.getYaw();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_pitch", description + " pitch",
                new OnlinePlaceholderReplacer<Float>(Float.class) {

                    @Override
                    public Float getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0F;
                        return loc.getPitch();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_chunk_x", description + " chunk X",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getX();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_chunk_z", description + " chunk Z",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getZ();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_chunk_entities", description + " chunk amount of entities",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getEntities().length;
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_chunk_tileentities",
                description + " chunk amount of tile entities", new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getTileEntities().length;
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_chunk_isloaded", description + " chunk is loaded",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return false;
                        return loc.getChunk().isLoaded();
                    }
                });
    }

    public static void convertOnlineStringListPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                           final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "#*",
                description + " (example: {" + placeholder.toLowerCase() + "#1} )",
                new OnlinePlaceholderReplacer<String>(String.class, placeholder) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        int idx = Integer.parseInt(event.getPlaceholder().replace(getArguments()[0].toString() + "#", ""));
                        String[] list = (String[]) replacer.getResult(event);
                        if (list == null)
                            return "";
                        if (list.length < idx)
                            return "";

                        return list[idx - 1];
                    }
                });
    }

    public static void convertOfflineStringListPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean playerrequired,
                                                            final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "#*",
                description + " (example: {" + placeholder.toLowerCase() + "#1} )", playerrequired,
                new PlaceholderReplacer<String>(String.class, placeholder) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        int idx = Integer.parseInt(event.getPlaceholder().substring(event.getPlaceholder().indexOf("#") + 1));
                        String[] list = (String[]) replacer.getResult(event);
                        if (list == null)
                            return "";
                        if (list.length < idx)
                            return "";
                        if (idx <= 0)
                            return "";
                        return list[idx - 1];
                    }
                });
    }

    public static void convertOnlineIntegerListPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                            final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "#*",
                description + " (example: {" + placeholder.toLowerCase() + "#1} )",
                new OnlinePlaceholderReplacer<Integer>(Integer.class, placeholder) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        int idx = Integer.parseInt(event.getPlaceholder().replace(getArguments()[0].toString() + "#", ""));
                        Integer[] list = (Integer[]) replacer.getResult(event);
                        if (list == null)
                            return 0;
                        if (list.length < idx)
                            return 0;

                        return list[idx - 1];
                    }
                });
    }

    public static void convertOfflineIntegerListPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean playerrequired,
                                                             final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "#*",
                description + " (example: {" + placeholder.toLowerCase() + "#1} )", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class, placeholder) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        int idx = Integer.parseInt(event.getPlaceholder().replace(getArguments()[0].toString() + "#", ""));
                        Integer[] list = (Integer[]) replacer.getResult(event);
                        if (list == null)
                            return 0;
                        if (list.length < idx)
                            return 0;

                        return list[idx - 1];
                    }
                });
    }

    public static void convertOnlineBooleanListPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                            final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "#*",
                description + " (example: {" + placeholder.toLowerCase() + "#1} )",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class, placeholder) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        int idx = Integer.parseInt(event.getPlaceholder().replace(getArguments()[0].toString() + "#", ""));
                        Boolean[] list = (Boolean[]) replacer.getResult(event);
                        if (list == null)
                            return false;
                        if (list.length < idx)
                            return false;

                        return list[idx - 1];
                    }
                }.setDefaultFalseOutput(replacer.getDefaultFalseOutput())
                        .setDefaultTrueOutput(replacer.getDefaultTrueOutput()));
    }

    public static void convertOfflineBooleanListPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean playerrequired,
                                                             final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "#*",
                description + " (example: {" + placeholder.toLowerCase() + "#1} )", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class, placeholder) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        int idx = Integer.parseInt(event.getPlaceholder().replace(getArguments()[0].toString() + "#", ""));
                        Boolean[] list = (Boolean[]) replacer.getResult(event);
                        if (list == null)
                            return false;
                        if (list.length < idx)
                            return false;

                        return list[idx - 1];
                    }
                }.setDefaultFalseOutput(replacer.getDefaultFalseOutput())
                        .setDefaultTrueOutput(replacer.getDefaultTrueOutput()));
    }

    public static void convertOfflineItemPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean playerrequired,
                                                      final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":amount", description + " amount", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getAmount();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":type", description + " type name", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return "";
                        return item.getType().name();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":typeid", description + " type id", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @SuppressWarnings("deprecation")
                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getTypeId();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":maxsize", description + " max size", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getMaxStackSize();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":durability", description + " durability", playerrequired,
                new PlaceholderReplacer<Short>(Short.class) {

                    @Override
                    public Short getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getDurability();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":maxdurability", description + " max durability",
                playerrequired, new PlaceholderReplacer<Short>(Short.class) {

                    @Override
                    public Short getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getType().getMaxDurability();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "remainingdurability", description + " remainingdurability",
                playerrequired, new PlaceholderReplacer<Short>(Short.class) {

                    @Override
                    public Short getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return (short) (item.getType().getMaxDurability() - item.getDurability());
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":maxstacksize", description + " max stack size",
                playerrequired, new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getType().getMaxStackSize();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":material", description + " material", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return "";
                        return item.getType().name();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":isblock", description + " is block", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isBlock();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":hasgravity", description + " has gravity", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().hasGravity();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":isburnable", description + " is burnable", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isBurnable();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":isedible", description + " is edible", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isEdible();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":isflammable", description + " is flammable", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isFlammable();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":isoccluding", description + " is occluding", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isOccluding();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":isrecord", description + " is record", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isRecord();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":issolid", description + " is solid", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isSolid();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":istransparent", description + " is transparent",
                playerrequired, new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isTransparent();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":displayname", description + " display name", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return "";
                        if (item.getItemMeta() == null)
                            return "";
                        return item.getItemMeta().getDisplayName();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + ":lore", description + " lore", playerrequired,
                new PlaceholderReplacer<String[]>(String[].class) {

                    @Override
                    public String[] getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return new String[0];
                        if (item.getItemMeta() == null)
                            return new String[0];
                        if (item.getItemMeta().getLore() == null)
                            return new String[0];
                        return ListUtils.listToArray(item.getItemMeta().getLore());
                    }
                });
    }

    public static void convertOfflineWorldPlaceholder(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean playerrequired,
                                                      final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder, description + " world name", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        World world = (World) replacer.getResult(event);
                        if (world == null)
                            return "";
                        return world.getName();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder + "_name", description + " world name", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        World world = (World) replacer.getResult(event);
                        if (world == null)
                            return "";
                        return world.getName();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder + "_UID", description + " world unique ID", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        World world = (World) replacer.getResult(event);
                        if (world == null)
                            return "";
                        return world.getUID().toString();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder + "_players", description + " players in world", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        World world = (World) replacer.getResult(event);
                        if (world == null)
                            return 0;
                        return world.getPlayers().size();
                    }
                });
    }

    public static void convertOnlineWorldPlaceholder(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                     final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder, description + " world name", new OnlinePlaceholderReplacer<String>(String.class) {

            @Override
            public String getResult(PlaceholderReplaceEvent event) {
                World world = (World) replacer.getResult(event);
                if (world == null)
                    return "";
                return world.getName();
            }
        });
        placeholderPackObj.addPlaceholder(placeholder + "_name", description + " world name",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        World world = (World) replacer.getResult(event);
                        if (world == null)
                            return "";
                        return world.getName();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder + "_UID", description + " world unique ID",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        World world = (World) replacer.getResult(event);
                        if (world == null)
                            return "";
                        return world.getUID().toString();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder + "_players", description + " players in world",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        World world = (World) replacer.getResult(event);
                        if (world == null)
                            return 0;
                        return world.getPlayers().size();
                    }
                });
    }

    public static void convertOnlineItemPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                     final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":amount", description + " amount",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getAmount();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":type", description + " type name",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return "";
                        return item.getType().name();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":typeid", description + " type id",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @SuppressWarnings("deprecation")
                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getTypeId();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":maxsize", description + " max size",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getMaxStackSize();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":durability", description + " durability",
                new OnlinePlaceholderReplacer<Short>(Short.class) {

                    @Override
                    public Short getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getDurability();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":maxdurability", description + " max durability",
                new OnlinePlaceholderReplacer<Short>(Short.class) {

                    @Override
                    public Short getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getType().getMaxDurability();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "remainingdurability", description + " remainingdurability",
                new OnlinePlaceholderReplacer<Short>(Short.class) {

                    @Override
                    public Short getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return (short) (item.getType().getMaxDurability() - item.getDurability());
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":maxstacksize", description + " max stack size",
                new OnlinePlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return 0;
                        return item.getType().getMaxStackSize();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":material", description + " material",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return "";
                        return item.getType().name();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":isblock", description + " is block",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isBlock();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":hasgravity", description + " has gravity",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().hasGravity();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":isburnable", description + " is burnable",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isBurnable();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":isedible", description + " is edible",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isEdible();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":isflammable", description + " is flammable",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isFlammable();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":isoccluding", description + " is occluding",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isOccluding();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":isrecord", description + " is record",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isRecord();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":issolid", description + " is solid",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isSolid();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":istransparent", description + " is transparent",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return false;
                        return item.getType().isTransparent();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":displayname", description + " display name",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return "";
                        if (item.getItemMeta() == null)
                            return "";
                        return item.getItemMeta().getDisplayName();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + ":lore", description + " lore",
                new OnlinePlaceholderReplacer<String[]>(String[].class) {

                    @Override
                    public String[] getResult(PlaceholderReplaceEvent event) {
                        ItemStack item = (ItemStack) replacer.getResult(event);
                        if (item == null)
                            return new String[0];
                        if (item.getItemMeta() == null)
                            return new String[0];
                        if (item.getItemMeta().getLore() == null)
                            return new String[0];
                        return ListUtils.listToArray(item.getItemMeta().getLore());
                    }
                });
    }

    public static void convertOfflineBooleanPlaceholders(final PlaceholderPack placeholderPackObj, final String placeholder, String description, boolean requiresplayer,
                                                         final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase(), description, requiresplayer,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        Boolean result = (Boolean) replacer.getResult(event);
                        String resultString = result
                                ? placeholderPackObj.getConfig().getString(
                                placeholder.replace("*", "").replace("@", "").replace("#", "") + ".true")
                                : placeholderPackObj.getConfig().getString(
                                placeholder.replace("*", "").replace("@", "").replace("#", "") + ".false");
                        return resultString;
                    }
                }.setDefaultTrueOutput(replacer.getDefaultTrueOutput())
                        .setDefaultFalseOutput(replacer.getDefaultFalseOutput()));
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_raw", description + " [Raw frue/false]", requiresplayer,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        Boolean result = (Boolean) replacer.getResult(event);
                        return String.valueOf(result);
                    }
                }.setDefaultTrueOutput("true").setDefaultFalseOutput("false"));
    }

    public static void convertOnlineBooleanPlaceholders(final PlaceholderPack placeholderPackObj, final String placeholder, String description,
                                                        final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase(), description, new OnlinePlaceholderReplacer<String>(String.class) {

            @Override
            public String getResult(PlaceholderReplaceEvent event) {
                Boolean result = (Boolean) replacer.getResult(event);

                String resultString = result
                        ? placeholderPackObj.getConfig()
                        .getString(placeholder.replace("*", "").replace("@", "").replace("#", "") + ".true")
                        : placeholderPackObj.getConfig()
                        .getString(placeholder.replace("*", "").replace("@", "").replace("#", "") + ".false");
                return resultString;
            }
        }.setDefaultTrueOutput(replacer.getDefaultTrueOutput())
                .setDefaultFalseOutput(replacer.getDefaultFalseOutput()));
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase(), description + " [Raw frue/false]",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        Boolean result = (Boolean) replacer.getResult(event);
                        return String.valueOf(result);
                    }
                }.setDefaultTrueOutput("true").setDefaultFalseOutput("false"));
    }

    public static void convertOnlineOfflinePlayerPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description,
                                                              final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase(), description, new OnlinePlaceholderReplacer<String>(String.class) {

            @Override
            public String getResult(PlaceholderReplaceEvent event) {
                OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                if (returnPlayer == null)
                    return "";
                return returnPlayer.getName();
            }
        });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_name", " player name",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                        if (returnPlayer == null)
                            return "";
                        return returnPlayer.getName();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_uuid", " player UUID",
                new OnlinePlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                        if (returnPlayer == null)
                            return "";
                        return returnPlayer.getUniqueId().toString();
                    }
                });
        placeholderPackObj.addPlaceholder(placeholder.toLowerCase() + "_isonline", " player is online",
                new OnlinePlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                        if (returnPlayer == null)
                            return false;
                        return returnPlayer.isOnline();
                    }
                });
    }

    public static void convertOfflineOfflinePlayerPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean playerrequired,
                                                               final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase(), description, playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                        if (returnPlayer == null)
                            return "";
                        return returnPlayer.getName();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_name", " player name", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                        if (returnPlayer == null)
                            return "";
                        return returnPlayer.getName();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_uuid", " player UUID", playerrequired,
                new PlaceholderReplacer<String>(String.class) {

                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                        if (returnPlayer == null)
                            return "";
                        return returnPlayer.getUniqueId().toString();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_isonline", " player is online", playerrequired,
                new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        OfflinePlayer returnPlayer = (OfflinePlayer) replacer.getResult(event);
                        if (returnPlayer == null)
                            return false;
                        return returnPlayer.isOnline();
                    }
                });
    }

    public static void convertOfflineLocationPlaceholders(PlaceholderPack placeholderPackObj, String placeholder, String description, boolean playerrequired,
                                                          final PlaceholderReplacer<?> replacer) {
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_x", description + " X", playerrequired,
                new PlaceholderReplacer<Double>(Double.class) {

                    @Override
                    public Double getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0.0;
                        return loc.getX();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_y", description + " X", playerrequired,
                new PlaceholderReplacer<Double>(Double.class) {

                    @Override
                    public Double getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0.0;
                        return loc.getY();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_z", description + " X", playerrequired,
                new PlaceholderReplacer<Double>(Double.class) {

                    @Override
                    public Double getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0.0;
                        return loc.getZ();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_blockx", description + " X rounded", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getBlockX();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_blocky", description + " Y rounded", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getBlockY();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_blockz", description + " Z rounded", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getBlockZ();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_world", description, playerrequired,
                new PlaceholderReplacer<World>(World.class) {

                    @Override
                    public World getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return null;
                        return loc.getWorld();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_yaw", description + " yaw", playerrequired,
                new PlaceholderReplacer<Float>(Float.class) {

                    @Override
                    public Float getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0F;
                        return loc.getYaw();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_pitch", description + " pitch", playerrequired,
                new PlaceholderReplacer<Float>(Float.class) {

                    @Override
                    public Float getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0F;
                        return loc.getPitch();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_chunk_x", description + " chunk X", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getX();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_chunk_z", description + " chunk Z", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getZ();
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_chunk_entities", description + " chunk amount of entities",
                playerrequired, new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getEntities().length;
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_chunk_tileentities",
                description + " chunk amount of tile entities", playerrequired,
                new PlaceholderReplacer<Integer>(Integer.class) {

                    @Override
                    public Integer getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return 0;
                        return loc.getChunk().getTileEntities().length;
                    }
                });
        placeholderPackObj.addOfflinePlaceholder(placeholder.toLowerCase() + "_chunk_isloaded", description + " chunk is loaded",
                playerrequired, new PlaceholderReplacer<Boolean>(Boolean.class) {

                    @Override
                    public Boolean getResult(PlaceholderReplaceEvent event) {
                        Location loc = (Location) replacer.getResult(event);
                        if (loc == null)
                            return false;
                        return loc.getChunk().isLoaded();
                    }
                });

    }
}
