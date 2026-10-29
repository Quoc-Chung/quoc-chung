INSERT IGNORE INTO employees (id, full_name, email, phone, position, salary) VALUES
    (1, 'Nguyen Van An', 'an.nguyen@example.com', '0901000001', 'Backend Developer', 25000000.00),
    (2, 'Tran Thi Binh', 'binh.tran@example.com', '0901000002', 'QA Engineer', 18000000.00),
    (3, 'Le Minh Chau', 'chau.le@example.com', '0901000003', 'Project Manager', 32000000.00);

INSERT IGNORE INTO app_users (id, username, full_name, email, phone, address) VALUES
    (1, 'admin_demo', 'Demo Administrator', 'admin.demo@example.com', '0902000001', 'Ho Chi Minh City'),
    (2, 'user_demo', 'Demo User', 'user.demo@example.com', '0902000002', 'Ha Noi'),
    (3, 'tester_demo', 'Demo Tester', 'tester.demo@example.com', '0902000003', 'Da Nang');
