create table 'informix'.project_reliability (
    project_id INT NOT NULL,
    user_id DECIMAL(10,0) NOT NULL,
    resolution_date DATETIME YEAR TO FRACTION NOT NULL,
    reliability_before_resolution DECIMAL(5,4),
    reliability_after_resolution DECIMAL(5,4) NOT NULL,
    reliability_on_registration DECIMAL(5,4),
    reliable_ind DECIMAL(1,0) NOT NULL
)
extent size 5000 next size 5000
lock mode row;

alter table 'informix'.project_reliability add constraint primary key
    (project_id, user_id)
    constraint project_reliability_pkey;
    
alter table 'informix'.project_reliability add constraint foreign key 
	(project_id)
	references 'informix'.project
	(project_id) 
	constraint fk_projectreliability_project_projectid;