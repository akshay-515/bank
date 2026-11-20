CREATE TABLE wallets (
    wallet_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_wallet_user
        FOREIGN KEY(user_id)
        REFERENCES users(user_id),

    CONSTRAINT chk_wallet_balance
        CHECK(balance >= 0),

    CONSTRAINT chk_wallet_status
        CHECK(status IN ('ACTIVE', 'BLOCKED'))
);