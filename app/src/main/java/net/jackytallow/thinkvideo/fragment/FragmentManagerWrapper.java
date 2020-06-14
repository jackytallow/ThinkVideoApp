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

    private volatile static FragmentManagerWrapper mInstance = null;

    public static FragmentManagerWrapper getInstance(){
        if (mInstance == null) {
            synchronized (FragmentManagerWrapper.class) {
                if (mInstance == null) {
                    mInstance = new FragmentManagerWrapper();
                }
            }
        }
        return mInstance;
    }

    private HashMap<String, Fragment> mHashMap = new HashMap<>();

    public Fragment createFragment(Class<?> clazz){
        return createFragment(clazz, true);
    }

    public Fragment createFragment(Class<?> clazz, boolean isobtain){
        Fragment fragment = null;
        String className = clazz.getName();
        if (mHashMap.containsKey(className)) {
            fragment = mHashMap.get(className);
        } else {
            try {
                fragment = (Fragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (isobtain) {
            mHashMap.put(className,fragment);
        }
        return fragment;
    }

}
