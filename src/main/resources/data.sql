INSERT INTO instructors
	(first_name, last_name, email, password_hash,
	department, specialization, hire_date,  is_active)
VALUES
	('Sarah', 'Johnson', 'sarah.johnson@example.com',
	'DEMO_ACCOUNT_NOT_FOR_LOGIN',
	'Computer Science', 'Web Development',
	'2022-09-01', TRUE);
	
INSERT INTO instructors
	(first_name, last_name, email, password_hash,
	department, specialization, hire_date,  is_active)
VALUES
	('Michael', 'Brown', 'michael.brown@example.com',
	'DEMO_ACCOUNT_NOT_FOR_LOGIN',
	'Mathematics', 'Statistics',
	'2021-01-15', TRUE);

INSERT INTO instructors
	(first_name, last_name, email, password_hash,
	department, specialization, hire_date,  is_active)
VALUES
	('Laura', 'Smith', 'laura.smith@example.com',
	'DEMO_ACCOUNT_NOT_FOR_LOGIN',
	'Business', 'Management',
	'2023-05-10', TRUE);
	
INSERT INTO courses
	(instructor_id, course_code, title, description,
	category, start_date, end_date, max_students,
	status, created_at)
VALUES
	((SELECT INSTRUCTOR_ID from INSTRUCTORS
	WHERE email = 'sarah.johnson@example.com'),
	'CPAN228',
	'Web Application Development',
	'Learn web development using Spring Boot and Thymeleaf.',
	'Programming',
	'2027-01-11', '2027-04-23',
	30, 'ACTIVE', CURRENT_TIMESTAMP);
	
INSERT INTO courses
	(instructor_id, course_code, title, description, 
	category,start_date, end_date, max_students, status,
	created_at)
VALUES
	((SELECT INSTRUCTOR_ID from INSTRUCTORS
	WHERE email = 'michael.brown@example.com'),
	'MATH210',
	'Applied Mathematics',
	'Explore mathematical concepts and practical problem solving.',
	'Mathematics',
	'2027-01-11', '2027-04-23',
	35, 'ACTIVE', CURRENT_TIMESTAMP);
	
INSERT INTO courses
	(instructor_id, course_code, title, description, 
	category,start_date, end_date, max_students, status,
	created_at)
VALUES
	((SELECT INSTRUCTOR_ID from INSTRUCTORS
	WHERE email = 'laura.smith@example.com'),
	'BUS101',
	'Introduction to Business',
	'Learn fundamental concepts of business and management.',
	'Business',
	'2027-05-10', '2027-08-20',
	40, 'INACTIVE', CURRENT_TIMESTAMP);
	