package top.defaults.gradientdrawabletuner.code;

import android.content.res.AssetManager;

import android.util.Log;
import org.eclipse.tm4e.core.registry.IThemeSource;

import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import top.defaults.gradientdrawabletuner.App;

public class CodeEditorColorSchemes {
    private static final String TAG = "CodeEditorColorSchemes";

    public static final String THEME_DRACULA = "dracula.json";
    public static final String THEME_GITHUB = "GitHub.tmTheme";
    public static final String[] THEMES = {THEME_DRACULA, THEME_GITHUB};

    static {
        AssetManager assets = App.getContext().getAssets();

        FileProviderRegistry.getInstance().addFileProvider(new AssetsFileResolver(assets));
        ThemeRegistry registry = ThemeRegistry.getInstance();

        for (String theme : THEMES) {
            String path = "textmate/themes/" + theme;
            try {
                registry.loadTheme(new ThemeModel(IThemeSource.fromInputStream(
                        FileProviderRegistry.getInstance().tryGetInputStream(path), path, null
                ), theme));
            } catch (Exception e) {
                Log.e(TAG, "Failed to load theme '" + theme + "'", e);
            }
        }
    }

    public static EditorColorScheme loadTextMateColorScheme(String theme) {
        EditorColorScheme scheme;

        try {
            ThemeRegistry.getInstance().setTheme(theme);
            scheme = TextMateColorScheme.create(ThemeRegistry.getInstance());
        } catch (Exception e) {
            Log.e(TAG, "Failed to load theme '" + theme + "', using defaults", e);
            scheme = new EditorColorScheme();
        }

        return scheme;
    }
}
