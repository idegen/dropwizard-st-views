import com.dropwizard.views.st.View;

public class NoAttributeView implements View {
    private String templateName;

    protected NoAttributeView(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }
}
