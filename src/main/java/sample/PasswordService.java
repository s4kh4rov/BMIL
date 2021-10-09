package sample;

import java.util.*;

public class PasswordService {
    private Map<String, KeyEntity> clampedKeys;
    private Map<OverlayType, Integer> overlays;
    private KeyEntity prevKeyEntity;
    private boolean isOverlay;

    public boolean isOverlay() {
        return isOverlay;
    }

    public void setOverlay(boolean overlay) {
        isOverlay = overlay;
    }

    public PasswordService() {
        clampedKeys = new HashMap<>();
        overlays = new HashMap<>();
        overlays.put(OverlayType.FIRST_TYPE, 0);
        overlays.put(OverlayType.SECOND_TYPE, 0);
        isOverlay = false;
    }

    public int clampedKeysSize() {
        return clampedKeys.size();
    }

    public void addClampedKey(String key, KeyEntity keyEntity) {
        clampedKeys.put(key, keyEntity);
    }

    public void clearClampedKeys(String key) {
        prevKeyEntity = clampedKeys.get(key);
        clampedKeys.remove(key);
    }

    public void checkOverlay(String key) {
        KeyEntity currentKeyEntity = clampedKeys.get(key);
        if (currentKeyEntity.getUpTime() > prevKeyEntity.getUpTime() && currentKeyEntity.getDownTime() > prevKeyEntity.getDownTime()) {
            overlays.put(OverlayType.FIRST_TYPE, overlays.get(OverlayType.FIRST_TYPE) + 1);
        }
        if (currentKeyEntity.getDownTime() < prevKeyEntity.getDownTime() && prevKeyEntity.getUpTime() < currentKeyEntity.getUpTime()) {
            overlays.put(OverlayType.SECOND_TYPE, overlays.get(OverlayType.SECOND_TYPE) + 1);
        }
        isOverlay = false;
    }

    public KeyEntity getKeyEntity(String key) {
        return clampedKeys.get(key);
    }

    public String getOverlayStatistic() {
        return "1 вид " + overlays.get(OverlayType.FIRST_TYPE) + " \n2 вид " + overlays.get(OverlayType.SECOND_TYPE);
    }

    private enum OverlayType {
        FIRST_TYPE,
        SECOND_TYPE
    }
}
