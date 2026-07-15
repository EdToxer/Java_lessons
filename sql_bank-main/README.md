# sql_bank

1. Найти активные счета в EUR, открытые после 2024-01-01
```sql
SELECT *
FROM accounts
WHERE currency = 'EUR'
  AND status = 'active'
  AND opened_at > '2024-01-01'
ORDER BY opened_at;
```
2. Вывести ФИО клиента, тип счета, валюту и статус счета
```sql
SELECT 
    c.full_name,
    a.account_type,
    a.currency,
    a.status
FROM clients c
INNER JOIN accounts a ON c.client_id = a.client_id;
```
3. Вывести всех клиентов и количество их счетов (включая 0)
```sql
SELECT 
    c.client_id,
    c.full_name,
    COUNT(a.account_id) as accounts_count
FROM clients c
LEFT JOIN accounts a ON c.client_id = a.client_id
GROUP BY c.client_id, c.full_name
ORDER BY accounts_count DESC;
```
4. Найти клиентов, у которых больше 2 активных счетов
```sql
SELECT 
    c.client_id,
    c.full_name,
    COUNT(a.account_id) as active_accounts_count
FROM clients c
INNER JOIN accounts a ON c.client_id = a.client_id
WHERE a.status = 'active'
GROUP BY c.client_id, c.full_name
HAVING COUNT(a.account_id) > 2;
```
5. Найти счета, у которых сумма входящих операций выше среднего по банку
```sql
WITH account_incoming AS (
    SELECT 
        account_id,
        SUM(CASE 
            WHEN txn_type IN ('deposit', 'transfer_in') 
            THEN amount 
            ELSE 0 
        END) as total_incoming
    FROM transactions
    GROUP BY account_id
),
avg_incoming AS (
    SELECT AVG(total_incoming) as bank_avg
    FROM account_incoming
)
SELECT 
    a.*,
    ai.total_incoming
FROM accounts a
INNER JOIN account_incoming ai ON a.account_id = ai.account_id
CROSS JOIN avg_incoming av
WHERE ai.total_incoming > av.bank_avg
ORDER BY ai.total_incoming DESC;
```
6. Топ-5 клиентов по сумме всех операций (оборот) за 2025 год
```sql
SELECT 
    c.client_id,
    c.full_name,
    SUM(t.amount) as total_turnover
FROM clients c
INNER JOIN accounts a ON c.client_id = a.client_id
INNER JOIN transactions t ON a.account_id = t.account_id
WHERE EXTRACT(YEAR FROM t.txn_date) = 2025
GROUP BY c.client_id, c.full_name
ORDER BY total_turnover DESC
LIMIT 5;
```
7. Определить "активность клиента" по количеству операций за последние 90 дней
```sql
SELECT 
    c.client_id,
    c.full_name,
    COUNT(t.transaction_id) as operations_last_90_days,
    CASE 
        WHEN COUNT(t.transaction_id) = 0 THEN 'inactive'
        WHEN COUNT(t.transaction_id) BETWEEN 1 AND 5 THEN 'low'
        WHEN COUNT(t.transaction_id) BETWEEN 6 AND 20 THEN 'medium'
        ELSE 'high'
    END as activity_level
FROM clients c
LEFT JOIN accounts a ON c.client_id = a.client_id
LEFT JOIN transactions t ON a.account_id = t.account_id 
    AND t.txn_date >= CURRENT_DATE - INTERVAL '90 days'
GROUP BY c.client_id, c.full_name
ORDER BY operations_last_90_days DESC;
```
8. Найти кредиты, по которым сумма успешных платежей < 50% от principal
```sql
SELECT 
    l.*,
    COALESCE(SUM(lp.amount) FILTER (WHERE lp.status = 'success'), 0) as total_paid,
    l.principal * 0.5 as half_principal
FROM loans l
LEFT JOIN loan_payments lp ON l.loan_id = lp.loan_id
GROUP BY l.loan_id
HAVING COALESCE(SUM(lp.amount) FILTER (WHERE lp.status = 'success'), 0) < l.principal * 0.5;
```
9. Показать все активные карты и кому они принадлежат
```sql
SELECT 
    c.full_name,
    cr.card_id,
    cr.card_type,
    a.account_id,
    cr.issued_at,
    cr.expires_at,
    CASE 
        WHEN cr.expires_at < CURRENT_DATE THEN 'Истёк срок'
        ELSE 'Действует'
    END as card_status
FROM cards cr
INNER JOIN accounts a ON cr.account_id = a.account_id
INNER JOIN clients c ON a.client_id = c.client_id
WHERE cr.status = 'active'
  AND cr.expires_at >= CURRENT_DATE
ORDER BY cr.expires_at;
```
10. Для каждого счета посчитать: количество операций и сумму списаний
```sql
SELECT 
    a.account_id,
    a.account_type,
    a.currency,
    COUNT(t.transaction_id) as total_transactions,
    SUM(CASE 
        WHEN t.txn_type IN ('withdrawal', 'transfer_out', 'fee') 
        THEN t.amount 
        ELSE 0 
    END) as total_withdrawals,
    AVG(CASE 
        WHEN t.txn_type IN ('withdrawal', 'transfer_out', 'fee') 
        THEN t.amount 
        ELSE NULL 
    END) as avg_withdrawal_amount
FROM accounts a
LEFT JOIN transactions t ON a.account_id = t.account_id
GROUP BY a.account_id, a.account_type, a.currency
ORDER BY total_withdrawals DESC;
```
