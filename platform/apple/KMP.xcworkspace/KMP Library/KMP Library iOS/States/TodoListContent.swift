import SwiftUI
import SharedDomain

struct TodoListContent: View {
    let state: TodoUIState
    let onRefresh: () -> Void
    let onUpdate: (Int32, String, Bool) -> Void
    let onDelete: (Int32) -> Void

    var body: some View {
        Group {
            switch state {
            case is TodoUIStateError:
                let errorState = state as! TodoUIStateError
                ErrorStateView(error: errorState.message, onRefresh: onRefresh)

            case is TodoUIStateLoading:
                LoadingStateView()

            case is TodoUIStateSuccess:
                let successState = state as! TodoUIStateSuccess
                if successState.todos.isEmpty {
                    EmptyStateView(onRefresh: onRefresh)
                } else {
                    SuccessStateView(
                        todos: successState.todos,
                        onUpdate: onUpdate,
                        onDelete: onDelete
                    )
                }

            default:
                EmptyView()
            }
        }
    }
}
