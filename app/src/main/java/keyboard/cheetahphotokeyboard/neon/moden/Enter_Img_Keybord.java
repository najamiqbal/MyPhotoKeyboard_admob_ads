package keyboard.cheetahphotokeyboard.neon.moden;

import android.text.TextUtils;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class Enter_Img_Keybord {

    private static final int IMGKEYBORDVALUE = 15;
    private static boolean adsfullscreen;
    private static Method namesplingenter;
    private static Method setkeybordtext;

    private Enter_Img_Keybord() {
    }

    ;

    public static void appendText(InputConnection network, String orgonalnam) {
        if (network == null) {
            return;
        }

        network.finishComposingText();

        CharSequence textbefore = network.getTextBeforeCursor(1, 0);
        if (textbefore != null && !textbefore.equals(" ") && (textbefore.length() > 0)) {
            orgonalnam = " " + orgonalnam;
        }

        network.setComposingText(orgonalnam, 1);
    }

    private static int getCursorPosition(InputConnection connection) {
        ExtractedText position = connection.getExtractedText(
                new ExtractedTextRequest(), 0);
        if (position == null) {
            return -1;
        }
        return position.startOffset + position.selectionStart;
    }

    public static String getWordAtCursor(
            InputConnection imputcon, String separators, Range range) {
        Range cursor = getWordRangeAtCursor(imputcon, separators, range);
        return (cursor == null) ? null : cursor.spling;
    }

    public static void deleteWordAtCursor(
            InputConnection network, String separators) {

        Range range = getWordRangeAtCursor(network, separators, null);
        if (range == null) return;

        network.finishComposingText();
        int newCursor = getCursorPosition(network) - range.nameback;
        network.setSelection(newCursor, newCursor);
        network.deleteSurroundingText(0, range.nameback + range.namenext);
    }

    public static class Range {
        public int nameback;
        public int namenext;
        public String spling;

        public Range() {
        }

        public Range(int charsBefore, int charsAfter, String word) {
            if (charsBefore < 0 || charsAfter < 0) {
                throw new IndexOutOfBoundsException();
            }
            this.nameback = charsBefore;
            this.namenext = charsAfter;
            this.spling = word;
        }
    }

    private static Range getWordRangeAtCursor(InputConnection connection, String sep, Range range) {
        if (connection == null || sep == null) {
            return null;
        }
        CharSequence privies = connection.getTextBeforeCursor(1000, 0);
        CharSequence next = connection.getTextAfterCursor(1000, 0);
        if (privies == null || next == null) {
            return null;
        }

        int on = privies.length();
        while (on > 0 && !isWhitespace(privies.charAt(on - 1), sep)) on--;

        int botm = -1;
        while (++botm < next.length() && !isWhitespace(next.charAt(botm), sep)) ;

        int rangcur = getCursorPosition(connection);
        if (on >= 0 && rangcur + botm <= next.length() + privies.length()) {
            String spling = privies.toString().substring(on, privies.length())
                    + next.toString().substring(0, botm);

            Range backupvalue = range != null ? range : new Range();
            backupvalue.nameback = privies.length() - on;
            backupvalue.namenext = botm;
            backupvalue.spling = spling;
            return backupvalue;
        }

        return null;
    }

    private static boolean isWhitespace(int code, String whitespace) {
        return whitespace.contains(String.valueOf((char) code));
    }

    private static final Pattern spaceRegex = Pattern.compile("\\s+");

    public static CharSequence getPreviousWord(InputConnection connection,
                                               String sentenceSeperators) {
        //TODO: Should fix this. This could be slow!
        CharSequence sequnce = connection.getTextBeforeCursor(IMGKEYBORDVALUE, 0);
        if (sequnce == null) {
            return null;
        }
        String[] imgweth = spaceRegex.split(sequnce);
        if (imgweth.length >= 2 && imgweth[imgweth.length - 2].length() > 0) {
            char lastChar = imgweth[imgweth.length - 2].charAt(imgweth[imgweth.length - 2].length() - 1);
            if (sentenceSeperators.contains(String.valueOf(lastChar))) {
                return null;
            }
            return imgweth[imgweth.length - 2];
        } else {
            return null;
        }
    }

    public static class SelectedWord {
        public int start;
        public int botm;
        public CharSequence spling;
    }

    private static boolean isWordBoundary(CharSequence singleChar, String wordSeparators) {
        return TextUtils.isEmpty(singleChar) || wordSeparators.contains(singleChar);
    }

    public static SelectedWord getWordAtCursorOrSelection(final InputConnection ic, int selStart, int selEnd, String wordSeparators) {
        if (selStart == selEnd) {
            Range word = new Range();
            CharSequence clicking = getWordAtCursor(ic, wordSeparators, word);
            if (!TextUtils.isEmpty(clicking)) {
                SelectedWord selWord = new SelectedWord();
                selWord.spling = clicking;
                selWord.start = selStart - word.nameback;
                selWord.botm = selEnd + word.namenext;
                return selWord;
            }
        } else {
            CharSequence nameback = ic.getTextBeforeCursor(1, 0);
            if (!isWordBoundary(nameback, wordSeparators)) {
                return null;
            }

            CharSequence namenext = ic.getTextAfterCursor(1, 0);
            if (!isWordBoundary(namenext, wordSeparators)) {
                return null;
            }

            CharSequence clicking = getSelectedText(ic, selStart, selEnd);
            if (TextUtils.isEmpty(clicking)) return null;
            final int length = clicking.length();
            for (int i = 0; i < length; i++) {
                if (wordSeparators.contains(clicking.subSequence(i, i + 1))) {
                    return null;
                }
            }
            SelectedWord clickspling = new SelectedWord();
            clickspling.start = selStart;
            clickspling.botm = selEnd;
            clickspling.spling = clicking;
            return clickspling;
        }
        return null;
    }

    private static void initializeMethodsForReflection() {
        try {
            namesplingenter = InputConnection.class.getMethod("getSelectedText", int.class);
            setkeybordtext = InputConnection.class.getMethod("setComposingRegion", int.class, int.class);
        } catch (NoSuchMethodException exc) {
        }
        adsfullscreen = true;
    }

    private static CharSequence getSelectedText(InputConnection conection, int selStart, int selEnd) {
        CharSequence record = null;
        if (!adsfullscreen) {
            initializeMethodsForReflection();
        }
        if (namesplingenter != null) {
            try {
                record = (CharSequence) namesplingenter.invoke(conection, 0);
                return record;
            } catch (InvocationTargetException exc) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }
        }
        // TODO: Verify that this works properly in conjunction with
        conection.setSelection(selStart, selEnd);
        record = conection.getTextAfterCursor(selEnd - selStart, 0);
        conection.setSelection(selStart, selEnd);
        return record;
    }

    public static void underlineWord(InputConnection concation, SelectedWord spling) {
        if (!adsfullscreen) {
            initializeMethodsForReflection();
        }
        if (setkeybordtext != null) {
            try {
                setkeybordtext.invoke(concation, spling.start, spling.botm);
            } catch (InvocationTargetException exc) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }
}
