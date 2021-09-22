package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimCommentListAdapter
import ru.netology.fmhandroid.adapter.OnCommentItemClickListener
import ru.netology.fmhandroid.databinding.FragmentOpenClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

@AndroidEntryPoint
class OpenClaimFragment : Fragment() {
    private lateinit var binding: FragmentOpenClaimBinding
    private val viewModel: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_open_claim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOpenClaimBinding.bind(view)

        val args: OpenClaimFragmentArgs by navArgs()
        val claim = args.argClaim

        viewModel.dataClaim?.value = claim.claim
        viewModel.dataExecutor.value = User(
            id = 0,
            login = "",
            password = "",
            firstName = "",
            lastName = "",
            middleName = "",
            phoneNumber = "",
            email = "",
            deleted = false
        )

        val adapter = ClaimCommentListAdapter(object : OnCommentItemClickListener {
            override fun onCard(claimComment: ClaimCommentWithCreator) {
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                        claimComment,
                        claim.claim.id!!
                    )
                findNavController().navigate(action)
            }
        })

        val statusProcessingMenu = PopupMenu(context, binding.statusProcessingImageButton)
        if (claim.claim.status == Claim.Status.OPEN) {
            statusProcessingMenu.inflate(R.menu.menu_status_processing_open)
            statusProcessingMenu.setOnMenuItemClickListener {
                when (it.itemId) {

                    R.id.take_to_work_list_item -> {
//                        TODO(
//                            "Доработать после реализации авторизации пользователь нажимая " +
//                                    "эту кнопку автоматом должен заноситься в executorId данной заявки"
//                        )
                        // Изменить запрос ниже после авторизации!!! Убрать хардкод executorId!!!
                        viewModel.dataClaim?.value?.status = Claim.Status.IN_PROGRESS
                        viewModel.dataExecutor.value = viewModel.dataExecutor.value!!.copy(lastName = "Dolgov", firstName = "Dmitriy", middleName = "Petrovich")

                        viewModel.updateClaim(claim.claim.copy(executorId = 1))

                        viewModel.changeClaimStatus(claim.claim.id!!, Claim.Status.IN_PROGRESS)
                        binding.statusLabelTextView.setText(R.string.in_progress)

                        // Изменить строку ниже. Здесь должен быть исполнитель в соответствии с ТЗ!!!
//                        binding.executorNameTextView.text = "Викторов Иван Петрович"
                        statusProcessingMenu.inflate(R.menu.menu_status_processing_in_progress)


                        viewModel.claimStatusChangeException.observe(viewLifecycleOwner, {
                            Toast.makeText(
                                requireContext(),
                                R.string.error,
                                Toast.LENGTH_LONG
                            ).show()
                        })
                        true
                    }

                    R.id.cancel_list_item -> {
                        // TODO("Также перепроверить после внедрения авторизации!!!
                        //  В частности прописать условие, что отменять заявку может только ее автор или Администратор")

                        viewModel.changeClaimStatus(claim.claim.id!!, Claim.Status.CANCELLED)
                        viewModel.claimStatusChangedEvent.observe(viewLifecycleOwner, {
                            binding.statusLabelTextView.setText(R.string.cancelled)
                            binding.statusProcessingImageButton.visibility = View.INVISIBLE
                            binding.editProcessingImageButton.visibility = View.INVISIBLE
                        })
                        viewModel.claimStatusChangeException.observe(viewLifecycleOwner, {
                            Toast.makeText(
                                requireContext(),
                                R.string.error,
                                Toast.LENGTH_LONG
                            ).show()
                        })
                        true
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            R.string.error,
                            Toast.LENGTH_LONG
                        ).show()
                        false
                    }
                }
            }
        } else if (claim.claim.status == Claim.Status.IN_PROGRESS) {
            statusProcessingMenu.inflate(R.menu.menu_status_processing_in_progress)
            statusProcessingMenu.setOnMenuItemClickListener {
                when (it.itemId) {

                    R.id.throw_off_list_item -> {
//                        TODO(
//                            "Также доработать после авторизации. В соответствии с ТЗ" +
//                                    "Раздел 4"
//                        )
                        viewModel.updateClaim(claim.claim.copy(executorId = 0))
                        viewModel.claimUpdatedEvent.observe(viewLifecycleOwner, {
                            viewModel.changeClaimStatus(claim.claim.id!!, Claim.Status.OPEN)
                            binding.statusLabelTextView.setText(R.string.open)
                            statusProcessingMenu.inflate(R.menu.menu_status_processing_open)

                            val action = OpenClaimFragmentDirections
                                .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                                    argComment = null,
                                    argClaimId = claim.claim.id
                                )
                            findNavController().navigate(action)
                        })

                        true
                    }

                    R.id.executes_list_item -> {
                        TODO(
                            "Также доработать после авторизации. В соответствии с ТЗ" +
                                    "Раздел 4"
                        )
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            R.string.error,
                            Toast.LENGTH_LONG
                        ).show()
                        false
                    }
                }
            }
        }

        binding.apply {

            viewModel.dataClaim?.observe(viewLifecycleOwner, {
                statusLabelTextView.text =
                    viewModel.dataClaim!!.value?.status?.name
//                executorNameTextView.text = if (it.executorId != 0) {
//                    "Васильев Иван Петрович"
//                } else {
//                    getText(R.string.not_assigned)
//                }
            })

            viewModel.dataExecutor?.observe(viewLifecycleOwner, {
                executorNameTextView.text = Utils.fullUserNameGenerator(
                    it.lastName.toString(),
                    it.firstName.toString(),
                    it.middleName.toString()
                )
            })

            if (claim.claim.status == Claim.Status.CANCELLED || claim.claim.status == Claim.Status.EXECUTED) {
                statusProcessingImageButton.visibility = View.INVISIBLE
                editProcessingImageButton.visibility = View.INVISIBLE
            }
            titleTextView.text = claim.claim.title
            executorNameTextView.text = if (claim.executor != null) {
                Utils.fullUserNameGenerator(
                    claim.executor!!.lastName.toString(),
                    claim.executor!!.firstName.toString(),
                    claim.executor!!.middleName.toString()
                )
            } else {
                getText(R.string.not_assigned)
            }

            planeDateTextView.text =
                claim.claim.planExecuteDate?.let { Utils.showDateTimeInOne(it) }

            statusLabelTextView.text = when (claim.claim.status) {
                Claim.Status.CANCELLED -> getString(R.string.cancel)
                Claim.Status.EXECUTED -> getString(R.string.executed)
                Claim.Status.IN_PROGRESS -> getString(R.string.in_progress)
                Claim.Status.OPEN -> getString(R.string.status_open)
                else -> "?"
            }

            descriptionTextView.text = claim.claim.description
            authorNameTextView.text = Utils.fullUserNameGenerator(
                claim.creator.lastName.toString(),
                claim.creator.firstName.toString(),
                claim.creator.middleName.toString()
            )
            createDataTextView.text =
                claim.claim.createDate?.let { Utils.showDateTimeInOne(it) }

            addImageButton.setOnClickListener {
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                        argComment = null,
                        argClaimId = claim.claim.id!!
                    )
                findNavController().navigate(action)
            }

            closeImageButton.setOnClickListener {
                findNavController().navigateUp()
            }

            statusProcessingImageButton.setOnClickListener {
                statusProcessingMenu.show()
            }

            editProcessingImageButton.setOnClickListener {
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimFragment(claim)
                findNavController().navigate(action)
            }
        }

        binding.claimCommentsListRecyclerView.adapter = adapter

        viewModel.claimCommentUpdatedEvent.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                viewModel.commentsData.collect {
                    adapter.submitList(it)
                }
            }
        })

        viewModel.claimCommentCreateExceptionEvent.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireContext(),
                R.string.error,
                Toast.LENGTH_LONG
            ).show()
        })

        lifecycleScope.launch {
            viewModel.commentsData.collect {
                adapter.submitList(it)
            }
        }
    }
}
