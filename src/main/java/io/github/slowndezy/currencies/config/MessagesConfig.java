package io.github.slowndezy.currencies.config;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigSection("messages")
@ConfigFile("config.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagesConfig {

    @Getter
    private static final MessagesConfig instance = new MessagesConfig();

    @ConfigField("incorrect-target")
    private String incorrectTagert;
    @ConfigField("incorrect-usage")
    private String incorrectUsage;
    @ConfigField("incorrect-value")
    private String incorrectValue;
    @ConfigField("no-permission")
    private String noPermission;
    @ConfigField("error")
    private String error;
    @ConfigField("help-command")
    private List<String> helpCommand;
    @ConfigField("help-admin-command")
    private List<String> helpAdminCommand;
    @ConfigField("money-command")
    private String moneyCommand;

    @ConfigField("money-pay-command.yourself-error")
    private String yourselfError;
    @ConfigField("money-pay-command.insufficient")
    private String insufficientAmount;
    @ConfigField("money-pay-command.paid")
    private String paid;
    @ConfigField("money-pay-command.received")
    private String received;

    @ConfigField("money-add-command")
    private String moneyAddCommand;
    @ConfigField("money-set-command")
    private String moneySetCommand;
    @ConfigField("money-withdraw-command")
    private String moneyWithdrawCommand;

    @ConfigField("money-top-command.max-in-top")
    private int maxInTop;
    @ConfigField("money-top-command.header")
    private List<String> header;
    @ConfigField("money-top-command.ranking-format")
    private String rankingFormat;
    @ConfigField("money-top-command.footer")
    private List<String> footer;

    public static <T> T get(Function<MessagesConfig, T> function) {
        return function.apply(instance);
    }
}
