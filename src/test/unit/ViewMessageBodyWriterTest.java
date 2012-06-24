import com.dropwizard.views.st.ViewMessageBodyWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class ViewMessageBodyWriterTest {

    private ViewMessageBodyWriter writer;

    @Before
    public void setUp() {
        writer = new ViewMessageBodyWriter("/Users/isabella/works/dropwizard-st-views/src/resources/templates", Integer.MAX_VALUE);
    }

    @Test
    public void shouldLoadStringTemplatesGroup() {
        //When
        StringTemplate template = writer.getTemplate(new TestViewWithAttributes("test.st", "SomeUrl", "A Text of Some Sort"));

        //Then
        assertThat(template.getTemplate(), startsWith("<li><a href="));
    }

    @Test
    public void shouldWriteTheTemplate() throws IOException {
        //Given
        TestViewWithAttributes view = new TestViewWithAttributes("test.st", "SomeUrl", "A Text of Some Sort");
        OutputStream responseStream = new ByteArrayOutputStream();

        //When
        writer.writeTo(view, null, null, null, null, null, responseStream);

        //Then
        assertThat(responseStream.toString(), startsWith("<li><a href="));
    }

    @Test
    public void shouldReplaceTheAttributes() throws IOException {
        //Given
        TestViewWithAttributes view = new TestViewWithAttributes("test.st", "SomeUrl", "A Text of Some Sort");
        OutputStream responseStream = new ByteArrayOutputStream();

        //When
        writer.writeTo(view, null, null, null, null, null, responseStream);

        //Then
        assertThat(responseStream.toString(), is("<li><a href=\"SomeUrl\">A Text of Some Sort</a></li>"));
    }

    @Test
    public void canDealWithEncodingProperly() throws IOException {
        //Given
        NoAttributeView view = new NoAttributeView("encodingTest.st");
        OutputStream responseStream = new ByteArrayOutputStream();

        //When
        writer.writeTo(view, null, null, null, null, null, responseStream);

        //Then
        assertThat(responseStream.toString(), startsWith("<body>\n" +
                "<style/>\n" +
                "\n" +
                "<h1>Story wall</h1>\n" +
                "\n" +
                "<div class=\"wall-column\">"));
    }
}
