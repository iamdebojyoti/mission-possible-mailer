package live.missionpossible.mail;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import live.missionpossible.MailContent;
import live.missionpossible.models.User;

public abstract class AbstractRegnMailPreparer implements MailPreparer {

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${application.cc.address}")
	private String ccAddress;
	
	@Override
	public MailContent prepareMail(User user) {
		Context context = prepareContext(user);
		MailContent content = new MailContent();
		
		content.setSubject(getSubject());
		content.setBody(templateEngine.process(getTemplateLocation(), context));
        content.setTo(user.getEmailId());
        content.setCc(ccAddress);
        getAttachmentNameLocation().ifPresent(pair ->content.setAttachment(pair));
        
		return content;
	}
	
	public abstract Context prepareContext(User user);
	
	public abstract String getTemplateLocation();
	
	public abstract String getSubject();
	
	public abstract Optional<Pair<String, InputStreamSource>> getAttachmentNameLocation();

}
