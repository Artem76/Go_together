package app.repository;

import app.entity.EmailAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by АРТЕМ on 11.09.2017.
 */
public interface EmailAttachmentRepository extends JpaRepository<EmailAttachment, Long> {
}
