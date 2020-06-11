package com.sdz.webview.command.mainprocess;

import com.sdz.webview.command.base.Commands;
import com.sdz.webview.utils.WebConstants;

public class BaseLevelCommands extends Commands {

    public BaseLevelCommands() {
    }

    @Override
    protected int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }
}
