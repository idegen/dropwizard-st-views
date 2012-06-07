import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ViewTest {
     @Test
      public void shouldFindStringTemplate() {
         //Given
         String templateName = "test.st";
         NoAttributeView view = new NoAttributeView(templateName);

         //When & Then
         assertThat(view.getTemplateName(), is("test"));
      }
}
