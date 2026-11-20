CREATE TABLE transactions (
    transaction_id BIGSERIAL NOT NULL PRIMARY KEY,
    from_wallet_id BIGINT,
    to_wallet_id BIGINT,
    amount DECIMAL(15,2) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'SUCCESS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_from_wallet_id
        FOREIGN KEY(from_wallet_id)
        REFERENCES wallets(wallet_id),

    CONSTRAINT fk_to_wallet_id
        FOREIGN KEY(to_wallet_id)
        REFERENCES wallets(wallet_id),

    CONSTRAINT chk_transaction_type
        CHECK(transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),

    CONSTRAINT chk_transaction_amount
        CHECK (amount > 0),

    CONSTRAINT chk_transaction_status
        CHECK (status IN ('SUCCESS', 'FAILED')),

    CONSTRAINT chk_transaction_wallets
        CHECK (
            from_wallet_id IS NOT NULL
            OR to_wallet_id IS NOT NULL
        )
);

    CREATE INDEX idx_transaction_from_wallet
    ON transactions(from_wallet_id);

    CREATE INDEX idx_transaction_to_wallet
    ON transactions(to_wallet_id);

    CREATE INDEX idx_transaction_created_at
    ON transactions(created_at);