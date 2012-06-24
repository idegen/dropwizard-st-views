package com.dropwizard.views.st;

import com.yammer.dropwizard.Bundle;
import com.yammer.dropwizard.config.Environment;

/**
 * A {@link Bundle} which enables the rendering of StringTemplate views by your service.
 *
 * <p>A view combines a StringTemplate template with a set of Java objects:</p>
 *
 * <pre><code>
 * public class PersonView extends View {
 *     private final Person person;
 *
 *     public PersonView(Person person) {
 *         super("profile.st");
 *         this.person = person;
 *     }
 *
 *     public Person getPerson() {
 *         return person;
 *     }
 * }
 * </code></pre>
 *
 *<p>The {@code "profile.st"} is the path of the template relative the t{@code templateFolder} path provided to the {@link Bundle}.
 *
 * <p>A resource method with a view would looks something like this:</p>
 *
 * <pre><code>
 * \@GET
 * public PersonView getPerson(\@PathParam("id") String id) {
 *     return new PersonView(dao.find(id));
 * }
 * </code></pre>
 *
 * <p>Stringtemplate templates look something like this:</p>
 *
 * <pre>{@code
 * <html>
 *     <body>
 *         <h1>Hello, $view.person.name</h1>
 *     </body>
 * </html>
 * }</pre>
 *
 * <p>In this template, {@code $view.person.name$} calls {@code getPerson().getName()}.
 *
 * @see <a href="http://www.antlr.org/wiki/display/ST/Five+minute+Introduction">String Template v3 Introduction</a>
 */
public class ViewBundle implements Bundle {
    private String templateFolder;
    private int refreshInterval;

    public ViewBundle(String templateFolder, int refreshInterval) {
        this.templateFolder = templateFolder;
        this.refreshInterval = refreshInterval;
    }

    @Override
    public void initialize(Environment environment) {
        environment.addProvider(new ViewMessageBodyWriter(templateFolder, refreshInterval));
    }
}
