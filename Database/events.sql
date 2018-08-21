SET GLOBAL event_scheduler = ON;
delimiter |
DROP EVENT IF exists documents_daily;
CREATE EVENT documents_daily
    ON SCHEDULE
      EVERY 1 DAY
      STARTS CURRENT_TIMESTAMP
    COMMENT 'document status = 0 if expired'
    DO
      BEGIN
		UPDATE driving_licence set `status`=0 where `status`=1 and valid_until<curdate();
        UPDATE passport set `status`=0 where `status`=1 and valid_until<curdate();
        UPDATE identity_card set `status`=0 where `status`=1 and valid_until<curdate();
      END |
delimiter ;