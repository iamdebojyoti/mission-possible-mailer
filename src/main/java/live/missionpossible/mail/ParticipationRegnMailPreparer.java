package live.missionpossible.mail;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import live.missionpossible.models.User;

@Component
public class ParticipationRegnMailPreparer extends AbstractRegnMailPreparer {

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
		return "participation";
	}

	@Override
	public String getSubject() {
		return "Greetings from Mission Possible!!!";
	}

	@Override
	public Optional<Pair<String, InputStreamSource>> getAttachmentNameLocation() {
		return Optional.ofNullable(new ImmutablePair<>("poster.jpg", new ClassPathResource("attachment.jpg")));
	}

}
