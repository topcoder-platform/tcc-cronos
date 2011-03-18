SELECT pr.project_id, ci.create_time,
          s.submission_status_id, pp1.scheduled_start_time, pp1.phase_status_id, pp1.actual_end_time,
          pp2.actual_end_time, pp3.actual_end_time, pr.passed_review_ind, pr.final_score FROM project_result pr
          INNER JOIN project p ON pr.project_id = p.project_id
          INNER JOIN component_inquiry ci ON ci.project_id = pr.project_id
          INNER JOIN project_phase pp1 ON pr.project_id = pp1.project_id
          LEFT OUTER JOIN project_phase pp2 ON pr.project_id = pp2.project_id
          INNER JOIN project_phase pp3 ON pr.project_id = pp3.project_id
          LEFT OUTER JOIN upload u ON pr.project_id = u.project_id
          LEFT OUTER JOIN submission s ON u.upload_id = s.upload_id
          LEFT OUTER JOIN resource r ON u.resource_id = r.resource_id
          LEFT OUTER JOIN resource_info ri ON r.resource_id = ri.resource_id
          WHERE pr.user_id = ci.user_id AND pr.user_id = 132456 AND p.project_category_id = 2 AND
          p.project_id NOT IN (SELECT ce.contest_id FROM contest_eligibility ce WHERE ce.is_studio = 0) AND
          p.project_id IN (SELECT pi.project_id FROM project_info pi WHERE pi.project_info_type_id = 13 AND
          (pi.value = 'Yes' or pi.value = 'yes')) AND
          pp1.phase_type_id = 2 AND
          pp1.scheduled_start_time >= TO_DATE('2005-01-04 09:00', '%Y-%m-%d %H:%M') AND
          pp2.phase_type_id = 3 AND
          pp3.phase_type_id = 6 AND
          u.upload_type_id = 1 AND
          s.submission_status_id != 5 AND
          ri.value = pr.user_id AND
          ri.resource_info_type_id = 1