SELECT DISTINCT pr.user_id FROM project_result pr, project_phase pp, project p
WHERE pr.project_id = pp.project_id
AND pp.phase_type_id = 2
AND pp.scheduled_start_time >= TO_DATE('2005-01-04 09:00', '%Y-%m-%d %H:%M')
AND pr.passed_review_ind = 1 AND pr.project_id = p.project_id AND p.project_category_id = 2
AND p.project_id NOT IN (SELECT ce.contest_id FROM contest_eligibility ce WHERE ce.is_studio = 0)
AND p.project_id IN (SELECT pi.project_id FROM project_info pi WHERE pi.project_info_type_id = 13
AND (pi.value = 'Yes' OR pi.value = 'yes'))