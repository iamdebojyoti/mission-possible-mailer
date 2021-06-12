package live.missionpossible.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import live.missionpossible.MailContent;
import live.missionpossible.models.User;

public abstract class AbstractMailPreparer implements MailPreparer {

	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public MailContent prepareMail(User user) {
		Context context = prepareContext(user);
		String body = templateEngine.process(getTemplateLocation(), context);
		
		MailContent content = new MailContent();
		content.setSubject(getSubject());
		content.setBody(body);
        content.setTo(user.getEmailId());
		
		return content;
	}
	
	public abstract Context prepareContext(User user);
	
	public abstract String getTemplateLocation();
	
	public abstract String getSubject();

}
