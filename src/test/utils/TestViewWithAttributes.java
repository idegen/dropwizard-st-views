import com.dropwizard.views.st.View;

public class TestViewWithAttributes implements View {
    private String templateName;
    private String url;
    private String linkText;

    protected TestViewWithAttributes(String templateName, String url, String linkText) {
        this.templateName = templateName;
        this.url = url;
        this.linkText = linkText;
    }

    public String getUrl() {
        return url;
    }

    public String getLinkText() {
        return linkText;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }
}
