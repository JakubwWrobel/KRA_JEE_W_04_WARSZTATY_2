# KRA_JEE_W_04_WARSZTATY_2
Warsztaty nr 2 - KURS JAVA


#OPIS
Program należy uruchomić z klasy Main1 która obsługuje panel administracyjny jak i panel użytkownika. Program posiada następującą budowę:

models - zawiera odzwierciedlenie bazy danych oraz specyfikację odpowiadających jej nazwą tabel
dao - posiada funkcjonalności dla odpowiadających jej nazwą tabeli
controller - wywołuje klasy dao zależnie od pobranych wartości, komunikuje się z użytkownikiem

Klasy dodatkowe:
Szkoła_Programowania/com/github/JakubwWrobel/addin
  Checking - waliduje poprawność adresu email oraz danych pobieranych od użytkownika
  GetConnection - nawiązuje połączenie z bazą danych

Baza danych została przygotowana w pliku:
Szkoła_Programowania/com/github/JakubwWrobel/addin/DBCreator/db.sql





#PYTANIA
1. czy należy w klasach dao unikać wszelkiej komunikacji z użytkownikiem w tym proste informacje zwrotne typu "Użytkownik nie istnieje". Starałem się tego trzymać jednak kosztował mnie to czasem podwójną pracę.
2. Mógłbym prosić o wyłumaczenie jak pobierać parametry z tablicy args parametrów metody main? "public static void main(String[] args)"
3. Jak należy poprawnie pobierać dane z innch klasy w moich klasach DAO, przykład jest z klasy SolutionDAO.java

//TAK LEPIEJ:
                if (resultSet.getInt("users_id") != 0) {
                    solution.setUsers_id(userDAO.read(resultSet.getInt("users_id")));
                } else {
                    solution.setUsers_id(null);
                }
//TAK?
             int userGroupId = resultSet.getInt("user_group_id");
                UserGroup userGroup = userGroupDAO.read(userGroupId);
                user.setUserGroup(userGroup);

//CZY MOZE TAK??
                int exerciseID = resultSet.getInt("exercise_id");
                Exercise exercise = exerciseDAO.read(exerciseID);
                if (exercise == null) {
                    solution.setExercise_id(null);
                } else {
                    solution.setExercise_id(exerciseDAO.read(resultSet.getInt("exercise_id")));

4. Wytłumaczyłbyś mi zastowosanie synchronized (this)?
