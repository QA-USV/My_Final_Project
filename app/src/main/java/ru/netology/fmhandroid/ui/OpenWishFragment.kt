package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentOpenWishBinding
import ru.netology.fmhandroid.domain.BusinessRules
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.dto.WishWithAllUsers
import ru.netology.fmhandroid.enum.ExecutionPriority
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.WishViewModel
import java.time.LocalDateTime

@AndroidEntryPoint
class OpenWishFragment : Fragment(R.layout.fragment_open_wish) {
    val viewModel: WishViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOpenWishBinding.inflate(inflater, container, false)

        // Временная переменная. После авторизации заменить на залогиненного юзера
        val user = User(
            id = 1,
            login = "User-1",
            password = "abcd",
            firstName = "Дмитрий",
            lastName = "Винокуров",
            middleName = "Владимирович",
            phoneNumber = "+79109008765",
            email = "Vinokurov@mail.ru",
            deleted = false
        )

        val args: OpenWishFragmentArgs by navArgs()
        val wishWithAllUsers = args.wishArgs

        val statusProcessingMenu = PopupMenu(context, binding.statusProcessingImageButton)
        statusProcessingMenu.inflate(R.menu.menu_wish_claim_status_processing)

        binding.apply {
            if (
                wishWithAllUsers?.wish?.status == Wish.Status.CANCELLED
                || wishWithAllUsers?.wish?.status == Wish.Status.EXECUTED
            ) {
                statusProcessingImageButton.visibility = View.INVISIBLE
                editProcessingImageButton.visibility = View.INVISIBLE
            }

            if (wishWithAllUsers?.wish?.status == Wish.Status.IN_PROGRESS) {
                editProcessingImageButton.visibility = View.INVISIBLE
            }

            // Изменить на залогиненного юзера и добавить в условие администратора
            if (
                wishWithAllUsers?.wish?.executorId != user.id
                && wishWithAllUsers?.wish?.status == Wish.Status.IN_PROGRESS
            ) {
                statusProcessingImageButton.visibility = View.INVISIBLE
            }

            titleLabelTextView.text = wishWithAllUsers?.wish?.title
            executorNameTextView.text = if (wishWithAllUsers?.executor != null) {
                Utils.fullUserNameGenerator(
                    wishWithAllUsers.executor.lastName.toString(),
                    wishWithAllUsers.executor.firstName.toString(),
                    wishWithAllUsers.executor.middleName.toString()
                )
            } else {
                getText(R.string.not_assigned)
            }
            patientNameTextView.text = Utils.fullUserNameGenerator(
                wishWithAllUsers?.patient?.lastName.toString(),
                wishWithAllUsers?.patient?.firstName.toString(),
                wishWithAllUsers?.patient?.middleName.toString()
            )
            planeDateTextView.text = wishWithAllUsers?.wish?.planExecuteDate?.let {
                Utils.showDateTimeInOne(
                    it
                )
            }
            if (wishWithAllUsers != null) {
                prioritization(wishWithAllUsers)
            }
            descriptionTextView.text = wishWithAllUsers?.wish?.description

        }


        return binding.root
    }

    private fun FragmentOpenWishBinding.prioritization(wishWithAllUsers: WishWithAllUsers) {
        val executionPriority = wishWithAllUsers.wish.planExecuteDate.let {
            BusinessRules.determiningPriorityLevelOfWish(
                LocalDateTime.now(),
                Utils.fromLongToLocalDateTime(it!!)
            )
        }

        when (executionPriority) {
            ExecutionPriority.HIGH ->
                statusIconImageView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.open_wish_fragment_priority_high
                    )
                )

            ExecutionPriority.MEDIUM ->
                statusIconImageView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.open_wish_fragment_priority_medium
                    )
                )

            ExecutionPriority.LOW ->
                statusIconImageView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.open_wish_fragment_priority_low
                    )
                )
        }
    }

}
