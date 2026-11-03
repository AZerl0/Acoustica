package com.azerl0.acoustica.gui.guis;

import com.azerl0.acoustica.api.speaker.Speaker;
import com.azerl0.acoustica.gui.AbstractGUI;
import com.azerl0.acoustica.playlist.PlayList;
import com.azerl0.acoustica.song.Song;
import com.azerl0.acoustica.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayListGUI extends AbstractGUI {
    private final PlayList playList;

    public PlayListGUI(PlayList playList) {
        super(27, Component.text("プレイリスト"));
        this.playList = playList;
    }

    @Override
    public void updateGUI() {
        clear();

        updatePlayingSongDisplay();

        addButton(9, getAbstractSimpleButtonItem(Material.BLUE_EGG, Component.text("前の曲を再生")), event -> {
            playList.previous();
        });

        addButton(11, setAmount(getAbstractSimpleButtonItem(Material.ARROW, Component.text("60秒巻き戻す")), 60), event -> {
            playList.getSpeaker().setTick(Math.max(playList.getSpeaker().getTick() - 60 * 20, 0));
        });

        addButton(12, setAmount(getAbstractSimpleButtonItem(Material.ARROW, Component.text("10秒巻き戻す")), 10), event -> {
            playList.getSpeaker().setTick(Math.max(playList.getSpeaker().getTick() - 10 * 20, 0));
        });


        updatePauseButton();

        addButton(14, setAmount(getAbstractSimpleButtonItem(Material.ARROW, Component.text("10秒進める")), 10), event -> {
            playList.getSpeaker().setTick(playList.getSpeaker().getTick() + 10 * 20);
        });

        addButton(15, setAmount(getAbstractSimpleButtonItem(Material.ARROW, Component.text("60秒進める")), 60), event -> {
            playList.getSpeaker().setTick(playList.getSpeaker().getTick() + 60 * 20);
        });

        addButton(17, getAbstractSimpleButtonItem(Material.BROWN_EGG, Component.text("次の曲を再生")), event -> {
            playList.skip();
        });

        addButton(18, getAbstractSimpleButtonItem(Material.GUNPOWDER, Component.text("音量を上げる"), List.of(Component.text("クリックして音量を 5% 上げる").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))), event -> {
            addVolume(0.05f);
            updateVolumeDisplay();
        });

        updateVolumeDisplay();

        addButton(20, getAbstractSimpleButtonItem(Material.GLOWSTONE_DUST, Component.text("音量を下げる"), List.of(Component.text("クリックして音量を 5% 下げる").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))), event -> {
            addVolume(-0.05f);
            updateVolumeDisplay();
        });
    }

    public void updatePauseButton() {
        addButton(13,
                playList.getSpeaker().isPause() ?
                        getAbstractSimpleButtonItem(Material.LIME_CANDLE, Component.text("再生")) :
                        getAbstractSimpleButtonItem(Material.RED_CANDLE, Component.text("一時停止")), event -> {
                    Speaker speaker = playList.getSpeaker();
                    if (speaker.isPause()) {
                        playList.play();
                    } else {
                        speaker.pause();
                        if (event.getWhoClicked() instanceof Player player) {
                            player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 1.0f, 1.0f);
                        }
                    }
                    updateGUI();
                }
        );
    }

    public void updatePlayingSongDisplay() {
        ItemStack itemStack = playList.getPlayingSongItem();
        if (itemStack == null) return;
        itemStack = itemStack.clone();
        Song playingSong = playList.getSpeaker().getSong();
        if (playingSong == null) return;


        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.itemName(Component.text("再生中").color(NamedTextColor.WHITE));
            meta.lore(List.of(
                    Component.text("♪-").append(playingSong.getSongName()).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                    Component.empty(),
                    Component.text(
                            ComponentUtil.getTimeText(playList.getSpeaker().getTick()) +
                                    " / " +
                                    ComponentUtil.getTimeText(playingSong.getNotes().getLast().getTick())
                    ).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
            ));
        }
        itemStack.setItemMeta(meta);

        addButton(4, itemStack, _ -> {});
    }

    public void updateVolumeDisplay() {
        int volume = (int) (playList.getSpeaker().getVolume() * 100);
        addButton(19, getAbstractSimpleButtonItem(Material.BELL, Component.text("音量"), List.of(
                Component.text("現在の音量: " + volume + "%").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
        )), _ -> {});
    }

    private void addVolume(float volume) {
        float speakerVolume = Math.clamp(
                playList.getSpeaker().getVolume() + volume,
                0.0f,
                1.0f
        );
        speakerVolume = (float) Math.floor(speakerVolume / 0.05f);
        speakerVolume = speakerVolume * 0.05f;
        playList.getSpeaker().setVolume(speakerVolume);
    }

    @Override
    public void open(Player player) {
        updateGUI();
        super.open(player);
    }

    private ItemStack setAmount(ItemStack itemStack, int amount) {
        itemStack.setAmount(amount);
        return itemStack;
    }
}
