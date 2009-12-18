insert into file_type_lu (sort, image_file_ind, file_type_desc, extension, file_type_id) values (10, 1, 'jpeg', 'jpeg', 1);
insert into contest_channel_lu (contest_channel_desc, contest_channel_id) values ('des', 1);
insert into contest_type_lu (contest_type_desc, require_preview_file, require_preview_image, contest_type_id) values ('desc', 't', 't', 1);
insert into contest_status_lu(contest_status_id, contest_status_desc) values(1, 'open');
insert into contest_detailed_status_lu (contest_detailed_status_desc, name, contest_status_id, contest_detailed_status_id) values ('desc', 'name', 1, 1);
insert into prize_type_lu (prize_type_desc, prize_type_id) values ('des', 1);
insert into medium_lu (medium_desc, medium_id) values ('des1', 1);
insert into medium_lu (medium_desc, medium_id) values ('des2', 2);
insert into resource (resource_name, resource_id) values ('acc1', 1);
insert into resource (resource_name, resource_id) values ('acc2', 2);
insert into contest_general_info (goals, target_audience,
 branding_guidelines, disliked_designs_websites, other_instructions,
  winning_criteria, contest_general_info_id) values ('goals', 'audiences', 'xxx', 'xxx', 'xxx', 'xxxx', 1);
insert into contest_multi_round_information (milestone_date, submitters_locked_between_rounds,
 round_one_introduction, round_two_introduction, contest_multi_round_information_id) values (CURRENT, 't', 'one', 'two', 1);
insert into contest_specifications (colors, fonts, layout_and_size, additional_requirements_and_restrictions, contest_specifications_id) values ('white', 'Arial', '10px', 'no', 1);
insert into contest_milestone_prize (create_date, amount, number_of_submissions, prize_type_id, contest_milestone_prize_id) values (CURRENT, 500.00, 1, 1, 1);

insert into contest (contest_channel_id, name, contest_type_id, project_id,
 tc_direct_project_id, contest_detailed_status_id, forum_id, contest_status_id,
  event_id, start_time, end_time, launch_immediately, deleted, winner_announcement_time,
   create_user_id, is_multi_round, non_winning_submissions_purchased, contest_id, contest_general_info_id, contest_multi_round_information_id, contest_specifications_id, contest_milestone_prize_id)
    values (1, 'name1', 1, 1, 1, 1, 1, 1, 1, CURRENT, CURRENT, 't', 'f', CURRENT, 1, 't', 't', 1, 1, 1, 1, 1);
insert into contest (contest_channel_id, name, contest_type_id, project_id,
 tc_direct_project_id, contest_detailed_status_id, forum_id, contest_status_id,
  event_id, start_time, end_time, launch_immediately, deleted, winner_announcement_time,
   create_user_id, is_multi_round, non_winning_submissions_purchased, contest_id, contest_general_info_id, contest_multi_round_information_id, contest_specifications_id, contest_milestone_prize_id)
    values (1, 'name2', 1, 1, 1, 1, 1, 1, 1, CURRENT, CURRENT, 't', 'f', CURRENT, 1, 't', 't', 2, 1, 1, 1, 1);
insert into contest (contest_channel_id, name, contest_type_id, project_id,
 tc_direct_project_id, contest_detailed_status_id, forum_id, contest_status_id,
  event_id, start_time, end_time, launch_immediately, deleted, winner_announcement_time,
   create_user_id, is_multi_round, non_winning_submissions_purchased, contest_id, contest_general_info_id, contest_multi_round_information_id, contest_specifications_id, contest_milestone_prize_id)
    values (1, 'name3', 1, 1, 1, 1, 1, 1, 1, CURRENT, CURRENT, 't', 'f', CURRENT, 1, 't', 't', 3, 1, 1, 1, 1);

insert into contest_medium_xref (contest_id, medium_id) values (1, 1);
insert into contest_medium_xref (contest_id, medium_id) values (2, 1);
insert into contest_medium_xref (contest_id, medium_id) values (3, 2);

insert into contest_resource_xref (contest_id, resource_id) values (1, 1);
insert into contest_resource_xref (contest_id, resource_id) values (1, 2);
insert into contest_resource_xref (contest_id, resource_id) values (2, 1);
insert into contest_resource_xref (contest_id, resource_id) values (2, 2);
insert into contest_resource_xref (contest_id, resource_id) values (3, 1);

insert into contest_status_relation values(1, 1, 1);


