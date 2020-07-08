package com.example.dataaccessauditing

import android.app.AppOpsManager
import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {

            //doSelfSyncAppOp()

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun doSelfSyncAppOp() {
        val appOpsManager = requireContext().getSystemService(AppOpsManager::class.java) as AppOpsManager
        val uid = Process.myUid()
        val tag = "MyTag"
        val message = "I'm dangerous and I know it"

        appOpsManager.noteOp(
            AppOpsManager.OPSTR_FINE_LOCATION,
            uid,
            requireContext().packageName,
            tag,
            message
        )
    }
}
