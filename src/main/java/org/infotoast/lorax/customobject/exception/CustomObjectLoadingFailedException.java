package org.infotoast.lorax.customobject.exception;

import org.infotoast.lorax.Lorax;

import java.io.IOException;

public class CustomObjectLoadingFailedException extends IOException {
    public CustomObjectLoadingFailedException(String message) {
        super(message);
        Lorax.plugin.getServer().getPluginManager().disablePlugin(Lorax.plugin);
    }
}
