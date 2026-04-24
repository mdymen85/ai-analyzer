-- DDL for Entry table
CREATE TABLE IF NOT EXISTS entry (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    operation_type VARCHAR(1) NOT NULL,
    branch BIGINT NOT NULL,
    account BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- DDL for Outbox table
CREATE TABLE IF NOT EXISTS outbox (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    body LONGTEXT NOT NULL,
    integrated BOOLEAN DEFAULT FALSE,
    attempts INT DEFAULT 0,
    error TEXT,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert 10,000 sample records into Entry table and corresponding Outbox records
DELIMITER //

CREATE PROCEDURE insert_sample_data()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE entry_id BIGINT;
    DECLARE entry_amount DECIMAL(19,2);
    DECLARE entry_operation VARCHAR(1);
    DECLARE entry_branch BIGINT;
    DECLARE entry_account BIGINT;
    DECLARE entry_created_at TIMESTAMP;
    DECLARE json_body LONGTEXT;

    WHILE i <= 10000 DO
        -- Generate random data
        SET entry_amount = ROUND(RAND() * 9999.99 + 0.01, 2);
        SET entry_operation = IF(RAND() < 0.5, 'D', 'C');
        SET entry_branch = FLOOR(RAND() * 100) + 1;
        SET entry_account = FLOOR(RAND() * 900000) + 100000;
        SET entry_created_at = NOW() - INTERVAL FLOOR(RAND() * 365) DAY;

        -- Insert into Entry table
        INSERT INTO entry (amount, operation_type, branch, account, created_at)
        VALUES (entry_amount, entry_operation, entry_branch, entry_account, entry_created_at);

        -- Get the generated ID
        SET entry_id = LAST_INSERT_ID();

        -- Create JSON body for Outbox
        SET json_body = CONCAT(
            '{"id":', entry_id, ',',
            '"amount":', entry_amount, ',',
            '"operationType":"', entry_operation, '",',
            '"branch":', entry_branch, ',',
            '"account":', entry_account, ',',
            '"createdAt":"', DATE_FORMAT(entry_created_at, '%Y-%m-%dT%H:%i:%s'), '"}'
        );

        -- Insert into Outbox table
        INSERT INTO outbox (body, integrated, attempts, status, created_at, updated_at)
        VALUES (json_body, FALSE, 0, 'NEW', entry_created_at, entry_created_at);

        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

-- Execute the procedure to insert sample data
CALL insert_sample_data();

-- Drop the procedure after use
DROP PROCEDURE insert_sample_data;
