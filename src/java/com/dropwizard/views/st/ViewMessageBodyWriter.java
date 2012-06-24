package com.dropwizard.views.st;

import com.google.common.base.Charsets;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.TimerContext;
import org.antlr.stringtemplate.NoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML})
public class ViewMessageBodyWriter implements MessageBodyWriter<View> {
    private StringTemplateGroup group;

    public ViewMessageBodyWriter(String templateFolder, int refreshInterval) {
        String groupName = "dropwizard-st";
        group = new StringTemplateGroup(groupName, templateFolder);
        group.setFileCharEncoding(Charsets.UTF_8.name());
        group.setRefreshInterval(refreshInterval);
        //TODO: ability for named templates registration
    }

    private static final String MISSING_TEMPLATE_MSG =
            "<html>" +
                    "<head><title>Missing Template</title></head>" +
                    "<body><h1>Missing Template</h1><p>{0}</p></body>" +
                    "</html>";

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return View.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(View view,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType) {
        //TODO: implement
        return -1;
    }

    @Override
    public void writeTo(View view,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream responseStream) throws IOException, WebApplicationException {
        //TODO: implement error handling
//        try {
//            final Configuration configuration = configurationCache.getUnchecked(type);
//            final Template template = configuration.getTemplate(view.getTemplateName(),
//                    detectLocale(headers));
//        } catch (TemplateException e) {
//            throw new ContainerException(e);
//        } catch (FileNotFoundException e) {
//            final String msg = MessageFormat.format(MISSING_TEMPLATE_MSG, e.getMessage());
//            throw new WebApplicationException(Response.serverError()
//                    .type(MediaType.TEXT_HTML_TYPE)
//                    .entity(msg)
//                    .build());
//        } finally {
//            context.stop();
//        }
        TimerContext context = Metrics.newTimer(view.getClass(), "rendering").time();
        StringTemplate template = getTemplate(view);
        template.setAttribute("view", view);
        OutputStreamWriter writer = new OutputStreamWriter(responseStream, Charsets.UTF_8);
        template.write(new NoIndentWriter(writer));
        writer.flush();
        writer.close();
        context.stop();
    }

    public StringTemplate getTemplate(View view) {
        return group.getInstanceOf(view.getTemplateName().replace(".st", ""));
    }
}