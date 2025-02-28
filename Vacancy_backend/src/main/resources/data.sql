insert into roles(role_name) values ('USER'), ('ADMIN');

--   username: karel, password: appel

insert into users(username, password,role_name) values ('dd0gan', '$2a$12$v3hpM1z6mh.ITK9UdFeeiOHOaRzvrlLCCGQc9tyZi718XWXWmLub6','USER');
insert into users(username, password,role_name) values ('karel', '$2a$12$v3hpM1z6mh.ITK9UdFeeiOHOaRzvrlLCCGQc9tyZi718XWXWmLub6','USER');
-- tien / tien
insert into users(username, password,role_name) values ('tien', '$2a$10$parO5dy6Nqu5.5MoiBSdzeqemP.sjPwyLVVW/xmthSTJYT4r/bzf2','ADMIN');
insert into users(username, password,role_name) values ('tien2', '$2a$10$parO5dy6Nqu5.5MoiBSdzeqemP.sjPwyLVVW/xmthSTJYT4r/bzf2','ADMIN');


INSERT INTO vacancies (title,username,organization,skills,description, hourly_rate, location, status, type, working_hour) VALUES('Data Entry','tien','HSBC','MS Office, Computer','he/she would be able to maintain daily office tasks related to data entry into Excel and  Word document files. It will be also useful if the candidate konw photo editing.', 2, 'NEW YORK', 'OPEN', 'Casual', 8);
INSERT INTO vacancies (title,username,organization,skills,description, hourly_rate, location, status, type, working_hour) VALUES('Web application','tien','HSBC','Java Spring','We require web developers who has experience of at least 6 month or 1 year in Java Spring.', 3, 'ROTTERDAM', 'OPEN', 'Casual', 10);
INSERT INTO vacancies (title,username,organization,skills,description, hourly_rate, location, status, type, working_hour) VALUES('Desktop application developer','tien','HSBC','VB.NET C#, SQL Server','We require desktop developers who has experience of at least 6 month in VB.NET Desktop (C# and VB.NET language).', 2, 'ROTTERDAM', 'OPEN', 'Casual', 7);
INSERT INTO vacancies (title,username,organization,skills,description, hourly_rate, location, status, type, working_hour) VALUES('Artificial Intelligent (AI) Developer','tien','Aviva plc','Machine Learning, Python',' job description involves designing, developing, and deploying AI solutions to improve business processes. AI jobs often require expertise in machine learning, data science, and programming', 4, 'PARIJS', 'OPEN', 'Casual', 12);
INSERT INTO vacancies (title,username,organization,skills,description, hourly_rate, location, status, type, working_hour) VALUES('Data Science Specialist','tien','Deloitte','Data Collection and analysis','Need professional who collects large amounts of data using analytical, statistical, and programmable skills', 3, 'ISTANBOEL', 'OPEN', 'Casual', 8);
INSERT INTO vacancies (title,username,organization,skills,description, hourly_rate, location, status, type, working_hour) VALUES('Database Administrator','tien','TCS',' MySQL','manages an organizations database system, ensuring that its secure, consistent, and performs well. They also work with developers to improve the database and troubleshoot issues', 2, 'BERLIJN', 'OPEN', 'Casual', 5);
INSERT INTO vacancies (title,username,organization,skills,description, hourly_rate, location, status, type, working_hour) VALUES('IT Manager','tien','TCS','Programming and Testing','he/she must have Interpersonal skills · Communication and motivation · Organisation and delegation · Forward planning and strategic thinking · Problem solving and decision-making', 1, 'BERLIJN', 'OPEN', 'Casual', 8);

