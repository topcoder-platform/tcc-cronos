CREATE PROCEDURE technology_list( i_comp_vers_id DECIMAL(12) )
    RETURNING LVARCHAR(1000);

    DEFINE o_cat_list LVARCHAR(1000);
    DEFINE t_cat_desc LVARCHAR(1000);

    LET o_cat_list = "";

    FOREACH
        SELECT tt.technology_name INTO t_cat_desc
        FROM comp_technology ct, technology_types tt
        WHERE ct.technology_type_id = tt.technology_type_id
        AND ct.comp_vers_id = i_comp_vers_id
        ORDER BY tt.technology_name

        IF LENGTH(o_cat_list) = 0 THEN
            LET o_cat_list = t_cat_desc;
        ELSE
            LET o_cat_list = o_cat_list || ", " || t_cat_desc;
        END IF
    END FOREACH

RETURN o_cat_list;

END PROCEDURE;