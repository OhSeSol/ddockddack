package com.ddockddack.domain.gameroom.response;

import com.ddockddack.domain.multigame.response.GameImageRes;
import com.ddockddack.domain.gameroom.entity.GameRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class GameRoomRes {

    private String token;
    private String pinNumber;
    private Long gameId;
    private String gameTitle;
    private String gameDescription;
    private List<GameImageRes> gameImages;
    private List<byte[]> byteImages;
    private Boolean isHost;

    @Builder
    public GameRoomRes(String token, String pinNumber, Long gameId, String gameTitle,
            String gameDescription, List<GameImageRes> gameImages, Boolean isHost) {
        this.token = token;
        this.pinNumber = pinNumber;
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gameImages = gameImages;
        this.isHost = isHost;
    }

    public static GameRoomRes of(GameRoom gameRoom, boolean isHost) {
        return GameRoomRes.builder()
                .pinNumber(gameRoom.getPinNumber())
                .gameId(gameRoom.getGameId())
                .gameTitle(gameRoom.getGameTitle())
                .gameDescription(gameRoom.getGameDescription())
                .gameImages(gameRoom.getGameImages().stream()
                        .collect(Collectors.toList()))
                .isHost(isHost)
                .build();
    }
}
