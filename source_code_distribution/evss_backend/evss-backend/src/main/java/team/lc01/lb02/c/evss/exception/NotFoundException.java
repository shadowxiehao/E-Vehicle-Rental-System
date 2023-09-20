package team.lc01.lb02.c.evss.exception;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg.concat(" not found."));
    }
}
