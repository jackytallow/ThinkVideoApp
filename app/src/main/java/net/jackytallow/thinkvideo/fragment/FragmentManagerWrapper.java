package net.jackytallow.thinkvideo.fragment;

import net.jackytallow.thinkvideo.base.BaseFragment;

import java.util.HashMap;

import androidx.fragment.app.Fragment;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/6/14
 */
public class FragmentManagerWrapper {

    private static volatile FragmentManagerWrapper mInstance = null;

    //双重校验
    public static FragmentManagerWrapper getInstance() {
        if (mInstance == null) {
            synchronized (FragmentManagerWrapper.class) {
                if (mInstance == null) {
                    mInstance = new FragmentManagerWrapper();
                }
            }
        }
        return mInstance;
    }

    private HashMap<String, BaseFragment> mHashMap = new HashMap<>();

    public Fragment createFragment(Class<?> clazz){
        return createFragment(clazz, true);
    }

    public BaseFragment createFragment(Class<?> clazz, boolean isObtain) {
        BaseFragment resultFragment = null;
        String className = clazz.getName();
        if (mHashMap.containsKey(className)) {
            try {
                resultFragment = (BaseFragment) Class.forName(className).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (isObtain) {
            mHashMap.put(className, resultFragment);
        }

        return resultFragment;
    }

}
