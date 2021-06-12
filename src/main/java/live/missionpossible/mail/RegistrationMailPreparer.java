package live.missionpossible.mail;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import live.missionpossible.models.User;

@Component
public class RegistrationMailPreparer extends AbstractMailPreparer {

	@Override
	public Context prepareContext(User user) {
		Context ctx = new Context();
		ctx.setVariable("name", user.getName());
		ctx.setVariable("regId", user.getRegistrationId());
		ctx.setVariable("eventsName", user.getEventName());

		return ctx;
	}

	@Override
	public String getTemplateLocation() {
		return "registrations";
	}

	@Override
	public String getSubject() {
		return "Greetings from Mission Possible!!!";
	}

}
