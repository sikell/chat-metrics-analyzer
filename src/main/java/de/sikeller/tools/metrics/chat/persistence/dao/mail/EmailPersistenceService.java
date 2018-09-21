package de.sikeller.tools.metrics.chat.persistence.dao.mail;

import de.sikeller.tools.metrics.chat.mail.model.Attachment;
import de.sikeller.tools.metrics.chat.mail.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmailPersistenceService {

    private final EmailRepository emailRepository;

    @Autowired
    public EmailPersistenceService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public void saveMail(Mail mail) {
        EmailDao dao = EmailDao.builder()
                .from(mail.getFrom())
                .to(mail.getTo())
                .cc(mail.getCc())
                .subject(mail.getSubject())
                .sentDate(mail.getSentDate())
                .message(mail.getMessage())
                .attachments(mail.getAttachments()
                        .stream().map(a -> AttachmentDao.builder()
                                .name(a.getName())
                                .contentType(a.getContentType())
                                .description(a.getDescription())
                                .content(a.getContent())
                                .build()).collect(Collectors.toList()))
                .processed(false)
                .build();
        emailRepository.save(dao);
    }

    public Optional<Mail> getMail(String id) {
        return emailRepository.findById(id).map(this::convert);
    }

    public List<Mail> retrieveUnprocessedMails() {
        List<EmailDao> mails = emailRepository.findAllByProcessedIsFalse();
        emailRepository.saveAll(mails.stream()
                .peek(m -> m.setProcessed(true))
                .collect(Collectors.toList()));
        return mails.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Mail convert(EmailDao m) {
        return new Mail(
                m.getFrom(),
                m.getTo(),
                m.getCc(),
                m.getSubject(),
                m.getSentDate(),
                m.getMessage(),
                m.getAttachments().stream()
                        .map(a -> new Attachment(
                                        a.getName(),
                                        a.getContentType(),
                                        a.getDescription(),
                                        a.getContent()
                                )
                        ).collect(Collectors.toList())
        );
    }

}
