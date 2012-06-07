package com.dropwizard.views.st;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Timer;
import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class View {
    private final String templateName;
    private final Timer renderingTimer;

    protected View(String templateName) {

        this.templateName = resolveName(templateName);
        this.renderingTimer = Metrics.newTimer(getClass(), "rendering");
    }

    private String resolveName(String templateName) {
        return templateName;
    }

    @JsonIgnore
    public String getTemplateName() {
        return templateName.replace(".st", "");
    }

    @JsonIgnore
    Timer getRenderingTimer() {
        return renderingTimer;
    }
}
