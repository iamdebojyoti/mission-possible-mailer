package live.missionpossible.mail;

import java.io.File;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import live.missionpossible.MailContent;
import live.missionpossible.models.User;

@Component
public class CertificationMailPreparer implements MailPreparer {
	
	@Autowired
	private TemplateEngine templateEngine;

	@Value("${application.cc.address}")
	private String ccAddress;
	
	@Value("${application.certification.location}")
	private String certificationLocation;
	
	@Override
	public MailContent prepareMail(User user) {
		Context ctx = new Context();
		ctx.setVariable("name", user.getName());
		MailContent content = new MailContent();
		
		content.setSubject(getSubject());
		content.setBody(templateEngine.process(getTemplateLocation(), ctx));
        content.setTo(user.getEmailId());
        content.setCc(ccAddress);
		content.setAttachment(new ImmutablePair<>("certificate.jpeg",
				new FileSystemResource(getOutputFile(user.getRegistrationId()))));

		return content;
	}
	
	private String getOutputFile(String regId) {
		return certificationLocation + File.separator + regId + ".jpeg";
	}
	
	public String getTemplateLocation() {
		return "certification";
	}

	public String getSubject() {
		return "Mission Possible - Certification";
	}

}
