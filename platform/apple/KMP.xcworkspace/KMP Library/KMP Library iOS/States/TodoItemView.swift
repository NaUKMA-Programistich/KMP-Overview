import SwiftUI
import SharedDomain


struct TodoItemView: View {
    let todo: Todo
    let onUpdate: (Int32, String, Bool) -> Void
    let onDelete: (Int32) -> Void
    let onEdit: () -> Void

    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 8)
                .fill(Color.white)
                .shadow(radius: 2)

            HStack {
                Button(action: {
                    onUpdate(todo.id, todo.title, !todo.completed)
                }) {
                    Image(systemName: todo.completed ? "checkmark.circle.fill" : "circle")
                        .foregroundColor(todo.completed ? .blue : .gray)
                }

                Text(todo.title)
                    .font(.body)

                Spacer()

                Button(action: {
                    onDelete(todo.id)
                }) {
                    Image(systemName: "trash")
                        .foregroundColor(.red)
                }
            }
            .padding(16)
        }
        .contentShape(Rectangle())
        .onTapGesture {
            onEdit()
        }
    }
}
