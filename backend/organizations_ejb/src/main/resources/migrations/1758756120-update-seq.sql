SELECT setval('organizations_id_seq', (SELECT MAX(id) FROM organizations) + 1);
