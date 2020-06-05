package com.ren.fade.model;

import com.ren.fade.controller.GameFieldController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameFieldLogic {

    private GameFieldController createRunes;
    private int score;
    public int moves;
    private int size;
    private  int combo;
    private Rune[][] runes;
    private List<Match> rowMatches;
    private List<Match> colMatches;

    public GameFieldLogic(GameFieldController createRunes, int size) {
        this.moves = 30;
        this.score = 0;
        this.combo = 0;
        this.size = size;
        this.createRunes = createRunes;
        this.runes = new Rune[this.size][this.size];
        this.rowMatches = new ArrayList<>();
        this.colMatches = new ArrayList<>();
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                this.runes[i][j] = new Rune();
                if (this.createRunes != null) {
                    this.runes[i][j] = this.createRunes.newRune(i, j);
                }
            }
        }
    }

    public int getSize() {
            return this.size;
        }

    public boolean checkIndex(int index) {
        return index >= 0 && index < this.size;
    }

    public Rune getRune(int i, int j) {
        return this.runes[i][j];
    }

    public Set<Rune> getAllRunes() {
        Set<Rune> all = new HashSet<>();
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                Rune rune = this.getRune(i, j);
                if (rune != null) {
                    all.add(rune);
                }
            }
        }
        return all;
    }

    public Set<Rune> deleteRunes(Set<Rune> runes) {
        Set<Rune> matched = new HashSet<>();
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                Rune thisRune = this.getRune(i, j);
                if (thisRune != null && runes.contains(thisRune)) {
                    this.runes[i][j] = null;
                    if (thisRune.type == Type.SPECIAL) {
                        this.moves += 3;
                        for (int newI = 0; newI < this.size; ++newI) {
                            Rune rune = this.getRune(newI, j);
                            if (rune != null && rune.activity == -1) {
                                matched.add(rune);
                            }
                        }
                        for (int newJ = 0; newJ < this.size; ++newJ) {
                            Rune rune = this.getRune(i, newJ);
                            if (rune != null && rune.activity == -1) {
                                matched.add(rune);
                            }
                        }
                    }
                }
            }
        }
        return matched;
    }

    private void swapRunes(int i1, int j1, int i2, int j2) {
        Rune rune = this.runes[i1][j1];
        this.runes[i1][j1] = this.runes[i2][j2];
        this.runes[i2][j2] = rune;
    }

    private void findMatches(boolean rows) {
        List<Match> matched = rows ? this.rowMatches : this.colMatches;
        matched.clear();
        Match current;
        for (int outer = 0; outer < this.size; ++outer) {
            current = new Match();
            for (int inner = 0; inner < this.size; ++inner) {
                Rune rune = this.runes[rows ? inner : outer][rows ? outer : inner];
                boolean validRune = rune != null && rune.activity == -1 && rune.kind >= 0;
                if (validRune && rune.kind == current.kind) {
                    ++current.length;
                    if (current.length == 3) {
                        current.i = rows ? inner - 2 : outer;
                        current.j = rows ? outer : inner - 2;
                        matched.add(current);
                    }
                } else {
                    if (current.length >= 3) {
                        current = new Match();
                    }
                    current.length = validRune ? 1 : 0;
                    current.kind = validRune ? rune.kind : -1;
                }
            }
        }
    }

    public Set<Rune> findMatches() {
        this.findMatches(true);
        this.findMatches(false);
        this.combo =  this.rowMatches.size() + this.colMatches.size();
        Set<Rune> matchedAll = new HashSet<>();
        //Collect all special runes first
        for (Match match: this.rowMatches) {
            for (int d = 0; d < match.length; ++d) {
                Rune rune = this.getRune(match.i + d, match.j);
                if (rune.type != Type.NORMAL) {
                    this.score += 10 * combo * match.length;
                    matchedAll.add(rune);
                }
            }
        }
        for (Match match: this.colMatches) {
            for (int d = 0; d < match.length; ++d) {
                Rune rune = this.getRune(match.i, match.j + d);
                if (rune.type != Type.NORMAL) {
                    this.score += 10 * combo * match.length;
                    matchedAll.add(rune);
                }
            }
        }
        // Marks one random rune among long matches with special type
        for (Match rowMatch: this.rowMatches) {
            for (Match colMatch: this.colMatches) {
                if ((rowMatch.i <= colMatch.i && colMatch.i < rowMatch.i + rowMatch.length &&
                        colMatch.j <= rowMatch.j && rowMatch.j < colMatch.j + colMatch.length)) {
                    score += combo * (rowMatch.length * (rowMatch.length - 2) * rowMatch.length * (colMatch.length - 2));
                    Rune rune = this.getRune(colMatch.i, rowMatch.j);
                    if (rune.type == Type.NORMAL) {
                        rune.type = Type.SPECIAL;
                    }
                }
            }
        }
        for (Match match: this.rowMatches) {
            score +=  combo * (match.length  * (match.length  - 1) * (match.length - 2) / 2);
            if (match.length > 3) {
                List<Rune> normalRunes = new ArrayList<>();
                for (int d = 0; d < match.length; ++d) {
                    Rune rune = this.getRune(match.i + d, match.j);
                    if (rune.type == Type.NORMAL) {
                        normalRunes.add(rune);
                    }
                } if (normalRunes.size() > 0) {
                    Rune rune = normalRunes.get((int)(Math.random() * normalRunes.size()));
                    if (match.length >= 4) {
                        rune.type = Type.SPECIAL;
                    }
                }
            }
        }
        for (Match match: this.colMatches) {
            score +=  combo * (match.length  * (match.length  - 1) * (match.length - 2) / 2);
            if (match.length > 3) {
                List<Rune> normalRunes = new ArrayList<>();
                for (int d = 0; d < match.length; ++d) {
                    Rune rune = this.getRune(match.i, match.j + d);
                    if (rune.type == Type.NORMAL) {
                        normalRunes.add(rune);
                    }
                }if (normalRunes.size() > 0) {
                    Rune rune = normalRunes.get((int)(Math.random() * normalRunes.size()));
                    if (match.length >= 4) {
                        rune.type = Type.SPECIAL;
                    }
                }
            }
        }
        //Then collect normal runes
        for (Match match: this.rowMatches) {
            for (int d = 0; d < match.length; ++d) {
                Rune rune = this.getRune(match.i + d, match.j);
                if (rune.type == Type.NORMAL) {
                    matchedAll.add(rune);
                }
            }
        }
        for (Match match: this.colMatches) {
            for (int d = 0; d < match.length; ++d) {
                Rune rune = this.getRune(match.i, match.j + d);
                if (rune.type == Type.NORMAL) {
                    matchedAll.add(rune);
                }
            }
        }
        return matchedAll;
    }

    public Set<Rune> findRunesToFall() {
        Set<Rune> runesToFall = new HashSet<>();
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                Rune thisRune = this.runes[i][j];
                if (thisRune != null)
                    continue;
                if (j == this.size - 1) {
                    thisRune = this.createRunes.newRune(i, j + 1);
                } else {
                    thisRune = this.runes[i][j + 1];
                    this.runes[i][j + 1] = null;
                }
                this.runes[i][j] = thisRune;
                if (thisRune == null)
                    continue;
                runesToFall.add(thisRune);
            }
        }
        return runesToFall;
    }


    public int getScore() {
            return this.score;
        }

    public int getMoves() {
        return this.moves;
    }


    public boolean tryToSwap(int i1, int j1, int i2, int j2) {
        this.swapRunes(i1, j1, i2, j2);
        this.findMatches(true);
        this.findMatches(false);
        boolean success = this.rowMatches.size() > 0 || this.colMatches.size() > 0;
        if (!success) {
            this.swapRunes(i1, j1, i2, j2);
            }
        return success;
    }
}

