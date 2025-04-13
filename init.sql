CREATE DATABASE IF NOT EXISTS membership;
CREATE DATABASE IF NOT EXISTS banking;
CREATE DATABASE IF NOT EXISTS money;
CREATE DATABASE IF NOT EXISTS remittance;
CREATE DATABASE IF NOT EXISTS payment;

GRANT ALL PRIVILEGES ON membership.* TO 'minipay_user'@'%';
GRANT ALL PRIVILEGES ON banking.* TO 'minipay_user'@'%';
GRANT ALL PRIVILEGES ON money.* TO 'minipay_user'@'%';
GRANT ALL PRIVILEGES ON remittance.* TO 'minipay_user'@'%';
GRANT ALL PRIVILEGES ON payment.* TO 'minipay_user'@'%';

FLUSH PRIVILEGES;