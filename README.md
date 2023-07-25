# MoH_inventory
cd quiz/
mvn clean install


`select cn.name as 'confirmed', o2.obs_datetime as 'dateCreated', o2.creator as 'creator' from obs o2
inner join (select o1.obs_group_id from obs o1 inner join encounter e inner join concept c where o1.voided = 'false' and o1.encounter_id = e.encounter_id and (o1.concept_id = c.concept_id and c.concept_id = 16) and o1.value_coded in (18)
group by o1.encounter_id, o1.obs_group_id) obsgroup on o2.obs_group_id = obsgroup.obs_group_id inner join concept c on o2.value_coded = c.concept_id inner join concept_name cn on (c.concept_id = cn.concept_id and cn.concept_name_type = 'FULLY_SPECIFIED')
where o2.concept_id = 15 group by cn.name;`
