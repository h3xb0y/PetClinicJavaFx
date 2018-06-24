USE PetClinic;

INSERT INTO `cabinet` VALUES (NULL,501,'Тип',5),
                            (NULL,302,'Тип',3),
                             (NULL,115,'Тип',1),
                              (NULL,505,'Тип',5),
                               (NULL,402,'Тип',4);
insert into `user` values ('doctor1','password1','admin'),
                        ('doctor2','password2','admin'),
                        ('owner1','password1','user'),
                        ('owner2','password2','user');
                              
INSERT INTO `specialization` VALUES (NULL, 'Ортопед'),
                            (NULL, 'Психоневролог'),
                            (NULL, 'Токсиколог');
INSERT INTO `doctor` VALUES (NULL, 'Сергей', 'Валерьев','+3800000000','Мужчина','doctor1', 1, 1),
                            (NULL, 'Сергей', 'Мартов','+3800000000','Мужчина','doctor2', 2, 1);
INSERT INTO `kind` VALUES (NULL, 'Кот'), (NULL, 'Собака'), (NULL, 'Хомяк'), (NULL, 'Птица'), (NULL, 'Кролик'), (NULL, 'Хорек')
                            , (NULL, 'Морская свинка'), (NULL, 'Черепаха');                                                  