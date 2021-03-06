package com.wyd.empire.battle.bean;
import java.util.List;
import com.wyd.protocol.data.AbstractData;
import com.wyd.session.AcceptSession;
public class WorldPlayer {
    private int           roomId;
    private int           battleId;
    private int           playerId;
    private String        playerName;
    private int           playerLevel;
    private int           sex;
    private String        suit_head;
    private String        suit_face;
    private String        suit_body;
    private String        suit_weapon;
    private int           weapon_type;
    private String        suit_wing;
    private String        player_title;
    private String        player_community;
    private int           maxHP;
    private int           maxPF;
    private int           maxSP;
    private int           attack;
    private int           bigSkillAttack;
    private int           critRate;         //暴击概率
    private int           defend;
    private int           bigSkillType;
    private int           explodeRadius;     // 获取爆破范围
    private int           injuryFree;        // 免伤
    private int           wreckDefense;      // 破防
    private int           reduceCrit;        // 免暴
    private int           reduceBury;        // 免坑
    private int           zsleve;            // 玩家转生等级
    private int           skillful;          // 玩家武器熟练度
    private int           petId;             // 宠物id，0表示无宠物
    private String        petIcon;           // 宠物icon
    private int           petType;           // 宠物类型
    private int           petSkillId;        // 宠物技能id
    private int           petProbability;    // 宠物攻击概率
    private int           petParam1;         // 宠物参数1
    private int           petParam2;         // 宠物参数2
    private String        petEffect;         // 宠物攻击特效名称
    private int[]         item_id;
    private int[]         item_used;
    private String[]      item_img;
    private String[]      item_name;
    private String[]      item_desc;
    private int[]         item_type;
    private int[]         item_subType;
    private int[]         item_param1;
    private int[]         item_param2;
    private int[]         item_ConsumePower;
    private int[]         specialAttackType;
    private int[]         specialAttackParam;
    private List<Integer> buffType;          // 按角色顺序填入
    private List<Integer> buffParam1;        // 不同类型参数意义不同(按角色顺序填入)
    private List<Integer> buffParam2;        // 预留参数(按角色顺序填入)
    private List<Integer> buffParam3;        // 预留参数(按角色顺序填入)
    private int           weapSkillCount;    // 武器技能数量
    private WeapSkill     weapSkill1 = null;
    private WeapSkill     weapSkill2 = null;
    private AcceptSession session = null;
    private long          actionTime;
    private String        serverName;        //玩家所在分区名称
    private int           petVersion;        // 宠物版本 1 旧版，2新版
    private int           force;             // 力量
    private int           armor;             // 护甲
    private int           fighting;          // 玩家的战斗力
    private int           winRate;           // 玩家的胜率(万分比)
    private int           constitution;      // 体质
    private int           agility;           // 敏捷
    private int           lucky;             // 幸运

    public WorldPlayer(AcceptSession session) {
        this.session = session;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getId() {
        return this.hashCode();
    }

    public int getPlayerId() {
        return this.playerId;
    }
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSuit_head() {
        return suit_head;
    }

    public void setSuit_head(String suit_head) {
        this.suit_head = suit_head;
    }

    public String getSuit_face() {
        return suit_face;
    }

    public void setSuit_face(String suit_face) {
        this.suit_face = suit_face;
    }

    public String getSuit_body() {
        return suit_body;
    }

    public void setSuit_body(String suit_body) {
        this.suit_body = suit_body;
    }

    public String getSuit_weapon() {
        return suit_weapon;
    }

    public void setSuit_weapon(String suit_weapon) {
        this.suit_weapon = suit_weapon;
    }

    public int getWeapon_type() {
        return weapon_type;
    }

    public void setWeapon_type(int weapon_type) {
        this.weapon_type = weapon_type;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getMaxPF() {
        return maxPF;
    }

    public void setMaxPF(int maxPF) {
        this.maxPF = maxPF;
    }

    public int getMaxSP() {
        return maxSP;
    }

    public void setMaxSP(int maxSP) {
        this.maxSP = maxSP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getBigSkillAttack() {
        return bigSkillAttack;
    }

    public void setBigSkillAttack(int bigSkillAttack) {
        this.bigSkillAttack = bigSkillAttack;
    }

    /**
     * 获取玩家暴击概率
     * @return
     */
    public int getCritRate() {
        return critRate;
    }

    public void setCritRate(int critRate) {
        this.critRate = critRate;
    }

    /**
     * 获取玩家暴击倍率
     * @return
     */
    public int getCritAttackRate() {
        return (int) ((skillful >= 10000 ? 1.3f : 1.2f) * 10000);
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getBigSkillType() {
        return bigSkillType;
    }

    public void setBigSkillType(int bigSkillType) {
        this.bigSkillType = bigSkillType;
    }

    public int getExplodeRadius() {
        return explodeRadius;
    }

    public void setExplodeRadius(int explodeRadius) {
        this.explodeRadius = explodeRadius;
    }

    public int[] getItem_id() {
        return item_id;
    }

    public void setItem_id(int[] item_id) {
        this.item_id = item_id;
    }

    public int[] getItem_used() {
        return item_used;
    }

    public void setItem_used(int[] item_used) {
        this.item_used = item_used;
    }

    public String[] getItem_img() {
        return item_img;
    }

    public void setItem_img(String[] item_img) {
        this.item_img = item_img;
    }

    public String[] getItem_name() {
        return item_name;
    }

    public void setItem_name(String[] item_name) {
        this.item_name = item_name;
    }

    public String[] getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String[] item_desc) {
        this.item_desc = item_desc;
    }

    public int[] getItem_type() {
        return item_type;
    }

    public void setItem_type(int[] item_type) {
        this.item_type = item_type;
    }

    public int[] getItem_subType() {
        return item_subType;
    }

    public void setItem_subType(int[] item_subType) {
        this.item_subType = item_subType;
    }

    public int[] getItem_param1() {
        return item_param1;
    }

    public void setItem_param1(int[] item_param1) {
        this.item_param1 = item_param1;
    }

    public int[] getItem_param2() {
        return item_param2;
    }

    public void setItem_param2(int[] item_param2) {
        this.item_param2 = item_param2;
    }

    public int[] getItem_ConsumePower() {
        return item_ConsumePower;
    }

    public void setItem_ConsumePower(int[] item_ConsumePower) {
        this.item_ConsumePower = item_ConsumePower;
    }

    public int[] getSpecialAttackType() {
        return specialAttackType;
    }

    public void setSpecialAttackType(int[] specialAttackType) {
        this.specialAttackType = specialAttackType;
    }

    public int[] getSpecialAttackParam() {
        return specialAttackParam;
    }

    public void setSpecialAttackParam(int[] specialAttackParam) {
        this.specialAttackParam = specialAttackParam;
    } 
    
    public List<Integer> getBuffType() {
        return buffType;
    }

    public void setBuffType(List<Integer> buffType) {
        this.buffType = buffType;
    }

    public List<Integer> getBuffParam1() {
        return buffParam1;
    }

    public void setBuffParam1(List<Integer> buffParam1) {
        this.buffParam1 = buffParam1;
    }

    public List<Integer> getBuffParam2() {
        return buffParam2;
    }

    public void setBuffParam2(List<Integer> buffParam2) {
        this.buffParam2 = buffParam2;
    }

    public List<Integer> getBuffParam3() {
        return buffParam3;
    }

    public void setBuffParam3(List<Integer> buffParam3) {
        this.buffParam3 = buffParam3;
    }

    public String getSuit_wing() {
        return suit_wing;
    }

    public void setSuit_wing(String suit_wing) {
        this.suit_wing = suit_wing;
    }

    public String getTitle() {
        return player_title;
    }

    public void setPlayer_title(String player_title) {
        this.player_title = player_title;
    }

    public String getGuildName() {
        return player_community;
    }

    public void setPlayer_community(String player_community) {
        this.player_community = player_community;
    }
    
    public int getWeapSkillCount() {
        return weapSkillCount;
    }

    public void setWeapSkillCount(int weapSkillCount) {
        this.weapSkillCount = weapSkillCount;
    }

    public WeapSkill getWeapSkill1() {
        return weapSkill1;
    }

    public void setWeapSkill1(WeapSkill weapSkill1) {
        this.weapSkill1 = weapSkill1;
    }

    public WeapSkill getWeapSkill2() {
        return weapSkill2;
    }

    public void setWeapSkill2(WeapSkill weapSkill2) {
        this.weapSkill2 = weapSkill2;
    }

    public int getInjuryFree() {
        return injuryFree;
    }

    public void setInjuryFree(int injuryFree) {
        this.injuryFree = injuryFree;
    }

    public int getWreckDefense() {
        return wreckDefense;
    }

    public void setWreckDefense(int wreckDefense) {
        this.wreckDefense = wreckDefense;
    }

    public int getReduceCrit() {
        return reduceCrit;
    }

    public void setReduceCrit(int reduceCrit) {
        this.reduceCrit = reduceCrit;
    }

    public int getReduceBury() {
        return reduceBury;
    }

    public void setReduceBury(int reduceBury) {
        this.reduceBury = reduceBury;
    }

    public int getZsleve() {
        return zsleve;
    }

    public void setZsleve(int zsleve) {
        this.zsleve = zsleve;
    }

    public int getSkillful() {
        return skillful;
    }

    public void setSkillful(int skillful) {
        this.skillful = skillful;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetIcon() {
        return petIcon;
    }

    public void setPetIcon(String petIcon) {
        this.petIcon = petIcon;
    }

    public int getPetType() {
        return petType;
    }

    public void setPetType(int petType) {
        this.petType = petType;
    }

    public int getPetSkillId() {
        return petSkillId;
    }

    public void setPetSkillId(int petSkillId) {
        this.petSkillId = petSkillId;
    }

    public int getPetProbability() {
        return petProbability;
    }

    public void setPetProbability(int petProbability) {
        this.petProbability = petProbability;
    }

    public int getPetParam1() {
        return petParam1;
    }

    public void setPetParam1(int petParam1) {
        this.petParam1 = petParam1;
    }

    public int getPetParam2() {
        return petParam2;
    }

    public void setPetParam2(int petParam2) {
        this.petParam2 = petParam2;
    }

    public String getPetEffect() {
        return petEffect;
    }

    public void setPetEffect(String petEffect) {
        this.petEffect = petEffect;
    }

    public void sendData(AbstractData data) {
        if (null != session) session.send(data);
    }

    /**
     * 随机获取本次攻击是否暴击
     * @return 1暴击，0不暴击
     */
    public int IsCriticalHit() {
        int ret = (int) (Math.random() * 10000);
        if (ret <= getCritRate()) {
            ret = 1;
        } else {
            ret = 0;
        }
        return ret;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }
    
    public String getServerId() {
        return session.getId();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getPetVersion() {
        return petVersion;
    }

    public void setPetVersion(int petVersion) {
        this.petVersion = petVersion;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getFighting() {
        return fighting;
    }

    public void setFighting(int fighting) {
        this.fighting = fighting;
    }

    public int getWinRate() {
        return winRate;
    }

    public void setWinRate(int winRate) {
        this.winRate = winRate;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getLucky() {
        return lucky;
    }

    public void setLucky(int lucky) {
        this.lucky = lucky;
    }
}
