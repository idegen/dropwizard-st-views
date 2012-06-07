import com.dropwizard.views.st.View;

public class TestViewWithAttributes extends View {
    private String url;
    private String linkText;

    protected TestViewWithAttributes(String templateName, String url, String linkText) {
        super(templateName);
        this.url = url;
        this.linkText = linkText;
    }

    public String getUrl() {
        return url;
    }

    public String getLinkText() {
        return linkText;
    }
}
