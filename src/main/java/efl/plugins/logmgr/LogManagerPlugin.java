package efl.plugins.logmgr;

import com.janetfilter.core.Environment;
import com.janetfilter.core.commons.DebugInfo;
import com.janetfilter.core.models.FilterRule;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginConfig;
import com.janetfilter.core.plugin.PluginEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LogManagerPlugin implements PluginEntry {
    private static final String PLUGIN_NAME = "LogMgr";
    private final List<MyTransformer> transformers = new ArrayList<>();
    private Environment environment;

    private void processDays(int days) {
        File dir = environment.getLogsDir();
        File[] logFiles = dir.listFiles();
        long currentTime = System.currentTimeMillis();

        if (null != logFiles && logFiles.length > 0) {
            List<File> logsList = Arrays.asList(Objects.requireNonNull(dir.listFiles()));
            logsList.sort((file, newFile) -> Long.compare(newFile.lastModified(), file.lastModified()));

            for (int i = 0; i < logsList.toArray().length; i++) {
                long difference = currentTime - logsList.get(i).lastModified();

                // 1 day == 86400000 ms
                if (difference > 86400000L * days) {
                    File expiredLogFile = logsList.get(i);
                    if (!expiredLogFile.delete())
                        DebugInfo.warn("Failed to delete expired logs: " + expiredLogFile);
                }
            }
        }
    }

    private void processNums(int nums) {
        File dir = environment.getLogsDir();
        File[] logFiles = dir.listFiles();

        if (null != logFiles && logFiles.length > 0) {
            List<File> logsList = Arrays.asList(Objects.requireNonNull(dir.listFiles()));
            logsList.sort((file, newFile) -> Long.compare(newFile.lastModified(), file.lastModified()));

            for (int i = 0; i < logsList.toArray().length; i++) {
                if (i >= nums - 1) {
                    File expiredLogFile = logsList.get(i);
                    if (!expiredLogFile.delete())
                        DebugInfo.warn("Failed to delete expired logs: " + expiredLogFile);
                }
            }
        }
    }

    @Override
    public void init(Environment environment, PluginConfig config) {
        this.environment = environment;

        String type, sValue;
        int value;

        List<FilterRule> rules = config.getBySection(PLUGIN_NAME);

        for (FilterRule rule : rules) {
            String sRule = rule.getRule();
            String[] parts = sRule.split("->", 2);
            if (2 != parts.length) {
                DebugInfo.warn("Invalid rule: " + sRule + ", skipped.");
                continue;
            }
            type = parts[0].trim();
            sValue = parts[1].trim();

            try {
                value = Integer.parseInt(sValue);
            } catch (NumberFormatException e) {
                DebugInfo.warn("No number was found in rule: " + sRule + ", skipped.");
                continue;
            }

            if (type.equalsIgnoreCase("days")) {
                processDays(value);
                break;
            } else if (type.equalsIgnoreCase("nums")) {
                processNums(value);
                break;
            }
            else DebugInfo.warn("Invalid type: " + type + ", skipped.");
        }
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public String getAuthor() {
        return "EFL";
    }

    @Override
    public String getVersion() {
        return "v1.0.0";
    }

    @SuppressWarnings("unused")
    @Override
    public String getDescription() {
        return "A plugin for the ja-netfilter, it can delete expired log files.";
    }

    @Override
    public List<MyTransformer> getTransformers() {
        return this.transformers;
    }
}
