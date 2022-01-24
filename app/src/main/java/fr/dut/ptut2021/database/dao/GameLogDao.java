package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.models.database.log.GameResultLog;

import java.util.List;

@Dao
public interface GameLogDao {

    //GAME RESULT LOG
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGameResultLog(GameResultLog gameResultLog);

    @Query("SELECT * FROM GameResultLog")
    List<GameResultLog> getAllGameResultLog();

    @Query("SELECT * FROM GameResultLog WHERE userId = :userId LIMIT 60")
    List<GameResultLog> getAllGameResultLogByUserLimit(int userId);

    @Query("SELECT * FROM GameResultLog WHERE userId = :userId AND endGameDate >= :minTime")
    List<GameResultLog> getAllGameResultLogAfterTime(int userId, long minTime);

    @Query("SELECT * FROM GameResultLog as l NATURAL JOIN Game AS g WHERE l.userId = :userId AND g.themeName LIKE :themeName AND l.endGameDate >= :minTime")
    List<GameResultLog> getAllGameResultLogAfterTimeByTheme(int userId, String themeName, long minTime);

    //@Query("SELECT * FROM Game as g NATURAL JOIN GameResultLog AS l WHERE l.userId = :userId AND g.themeName LIKE :themeName")
    //List<Game> getGameMostPlayedByTheme(int userId, String themeName);

    @Query("SELECT * FROM GameResultLog WHERE userId = :userId AND gameId = :gameId")
    List<GameResultLog> getAllGameResultLogByGame(int userId, int gameId);

    @Query("SELECT count(*) FROM GameResultLog WHERE userId = :userId AND gameId = :gameId")
    int getGameResultLogNbGame(int userId, int gameId);

    @Query("SELECT avg(stars) FROM GameResultLog WHERE userId = :userId")
    float getGameResultLogAvgStars(int userId);

    @Query("SELECT count(*) FROM GameResultLog WHERE userId = :userId")
    int getGameResultLogNb(int userId);

    @Query("SELECT * FROM GameResultLog WHERE userId = :userId AND gameId = :gameId AND subGameId = :subGameId")
    List<GameResultLog> getAllGameResultLogBySubGame(int userId, int gameId, int subGameId);

    default boolean tabGameResultLogIsEmpty(int userId, int gameId) {
        return getAllGameResultLogByGame(userId, gameId).isEmpty();
    }

    //GAME LOG
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGameLog(GameLog gameLog);

    @Query("SELECT * FROM GameLog")
    List<GameLog> getAllGameLog();

    //@Query("SELECT * FROM GameLog WHERE userId = :userId AND ")
    //List<GameLog> getGameLogByDate(int userId, long startDate);


    //For WordWithHoleData
    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleData AS w WHERE w.userId = :userId")
    List<GameLog> getWWHLogByUser(int userId);

    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleData AS w WHERE w.userId = :userId AND g.gameId = :gameId")
    List<GameLog> getWWHLogByGame(int userId, int gameId);

}
