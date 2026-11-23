CREATE TABLE transactions(
    transaction_id BIGSERIAL PRIMARY KEY,
    from_wallet_id BIGINT,
    to_wallet_id BIGINT,
    amount DECIMAL(15,2) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    transaction_status VARCHAR(20) NOT NULL DEFAULT 'SUCCESS',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_from_wallet
        FOREIGN KEY (from_wallet_id)
        REFERENCES wallets(wallet_id),

    CONSTRAINT fk_transaction_to_wallet
        FOREIGN KEY (to_wallet_id)
            REFERENCES wallets(wallet_id),

    CONSTRAINT chk_transaction_amount
        CHECK (amount > 0),

    CONSTRAINT chk_transaction_type
        CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),

    CONSTRAINT chk_transaction_status
        CHECK (transactions.transaction_status IN ('SUCCESS', 'FAILED')),

    CONSTRAINT chk_transaction_wallets
        CHECK (
            from_wallet_id IS NOT NULL
                OR to_wallet_id IS NOT NULL
            )
);

CREATE INDEX idx_transactions_from_wallet
    ON transactions(from_wallet_id);

CREATE INDEX idx_transactions_to_wallet
    ON transactions(to_wallet_id);

CREATE INDEX idx_transactions_created_at
    ON transactions(created_at);